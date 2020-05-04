package com.maxiaoda.xwshop.service;

import com.maxiaoda.xwshop.entity.TelAndCode;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class TelVerificationService {
    private static Pattern TEL_PATTERN = Pattern.compile("^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$");

    /**
     * 验证输入的参数是否合法：
     * tel必须存在且为中国大陆合法手机号
     *
     * @param param 输入参数
     * @return true 合法，否则 false
     */
    public boolean verifyTelParameter(TelAndCode param) {
        if (param == null) {
        return false;
        }else if (param.getTel()==null){
            return false;
        }else {
            return TEL_PATTERN.matcher(param.getTel()).find();
        }
    }
}
