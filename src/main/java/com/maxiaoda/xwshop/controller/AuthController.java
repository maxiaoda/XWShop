package com.maxiaoda.xwshop.controller;

import com.maxiaoda.xwshop.entity.TelAndCode;
import com.maxiaoda.xwshop.service.AuthService;
import com.maxiaoda.xwshop.service.TelVerificationService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;
    private final TelVerificationService telVerificationService;

    @Autowired
    public AuthController(AuthService authService,
                          TelVerificationService telVerificationService) {
        this.authService = authService;
        this.telVerificationService = telVerificationService;
    }


    @PostMapping("/code")
    public void code(@RequestBody TelAndCode telAndCode,
                     HttpServletResponse response) {
        if (telVerificationService.verifyTelParameter(telAndCode)) {
            authService.sendVerificationCode(telAndCode.getTel());
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
    }

    @PostMapping("/login")
    public void login(@RequestBody TelAndCode telAndCode) {
        UsernamePasswordToken token = new UsernamePasswordToken(
                telAndCode.getTel(),
                telAndCode.getCode());

        token.setRememberMe(true);
        SecurityUtils.getSubject().login(token);
    }
}
