package com.maxiaoda.xwshop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinsawicki.http.HttpRequest;
import com.maxiaoda.xwshop.entity.LoginResponse;
import com.maxiaoda.xwshop.service.TelVerificationServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

import static com.maxiaoda.xwshop.service.TelVerificationServiceTest.VALID_PARAMETER;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = XWShopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.yml")
public class AuthIntegrationTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    Environment environment;

    @Test
    public void loginLogoutTest() throws JsonProcessingException {

        // 最开始默认的情况下，访问/api/status 出去未登录状态
        String statusResponse = HttpRequest.get(getUrl("/api/status"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body();

        LoginResponse response = objectMapper.readValue(statusResponse, LoginResponse.class);
        Assertions.assertFalse(response.isLogin());

        // 发送验证码
        int responseCode = HttpRequest.post(getUrl("/api/code"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .send(objectMapper.writeValueAsString(TelVerificationServiceTest.VALID_PARAMETER_CODE))
                .code();

        Assertions.assertEquals(HTTP_OK, responseCode);

        // 带着验证码登录，得到Cookie
        Map<String, List<String>> responseHeaders = HttpRequest.post(getUrl("/api/login"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .send(objectMapper.writeValueAsString(TelVerificationServiceTest.VALID_PARAMETER_CODE))
                .headers();

        List<String> setCookie = responseHeaders.get("Set-Cookie");
        String sessionId = getSessionIdFromSetCookie(setCookie.stream()
                .filter(cookie -> cookie.contains("JSESSIONID"))
                .findFirst()
                .get());

        // 带着Cookie访问 /api/status 应处于登录状态
        statusResponse = HttpRequest.get(getUrl("/api/status"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Cookie", sessionId)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body();

        response = objectMapper.readValue(statusResponse, LoginResponse.class);
        Assertions.assertTrue(response.isLogin());
        Assertions.assertEquals(VALID_PARAMETER.getTel(), response.getUser().getTel());
        Assertions.assertNotNull(setCookie);

        // 调用/api/logout
        HttpRequest.post(getUrl("/api/logout"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Cookie", sessionId)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .code();

        // 再次带着Cookie访问/api/status 恢复为未登录状态
        statusResponse = HttpRequest.get(getUrl("/api/status"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Cookie", sessionId)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body();

        response = objectMapper.readValue(statusResponse, LoginResponse.class);
        Assertions.assertFalse(response.isLogin());
    }

    private String getSessionIdFromSetCookie(String setCookie) {
        int setColonIndex = setCookie.indexOf(";");
        return setCookie.substring(0, setColonIndex);
    }

    @Test
    public void returnHttpOkWhenParameterIsCorrect() throws JsonProcessingException {
        int responseCode = HttpRequest.post(getUrl("/api/code"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .send(objectMapper.writeValueAsString(VALID_PARAMETER))
                .code();

        Assertions.assertEquals(HTTP_OK, responseCode);
    }

    @Test
    public void returnHttpBadWhenParameterIsCorrect() throws JsonProcessingException {
        int responseCode = HttpRequest.post(getUrl("/api/code"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .send(objectMapper.writeValueAsString(TelVerificationServiceTest.EMPTY_TEL))
                .code();

        Assertions.assertEquals(HTTP_BAD_REQUEST, responseCode);
    }

    private String getUrl(String apiName) {
        // 获取集成测试的端口号
        return "http://localhost:" + environment.getProperty("local.server.port") + apiName;
    }

}
