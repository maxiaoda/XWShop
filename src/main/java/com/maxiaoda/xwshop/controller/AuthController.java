package com.maxiaoda.xwshop.controller;

import com.maxiaoda.xwshop.entity.TelAndCode;
import com.maxiaoda.xwshop.service.AuthService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/code")
    public void code(@RequestBody TelAndCode telAndCode) {
        authService.sendVerificationCode(telAndCode.getTel());

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
