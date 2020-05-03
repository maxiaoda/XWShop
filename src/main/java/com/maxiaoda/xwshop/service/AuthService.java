package com.maxiaoda.xwshop.service;

import com.maxiaoda.xwshop.generate.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;
    private final VerificationCheckService verificationCheckService;
    private final SmsCodeService smsCodeService;

    @Autowired
    public AuthService(UserService userService,
                       VerificationCheckService verificationCheckService,
                       SmsCodeService smsCodeService) {
        this.userService = userService;
        this.verificationCheckService = verificationCheckService;
        this.smsCodeService = smsCodeService;
    }

    public void sendVerificationCode(String tel) {
        userService.createUserIfNotExist(tel);
        String correctCode = smsCodeService.sendSmsCode(tel);
        verificationCheckService.addCode(tel,correctCode);

    }
}
