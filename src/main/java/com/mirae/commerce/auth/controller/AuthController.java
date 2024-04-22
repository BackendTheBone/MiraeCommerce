package com.mirae.commerce.auth.controller;

import com.mirae.commerce.auth.dto.RefreshTokenRequest;
import com.mirae.commerce.auth.dto.LogoutRequest;
import com.mirae.commerce.auth.jwt.Jwt;
import com.mirae.commerce.auth.service.AuthService;
import com.mirae.commerce.mail.service.MailService;
import com.mirae.commerce.auth.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Jwt> login(LoginRequest loginRequest) {
        return new ResponseEntity<>(
                authService.login(loginRequest),
                HttpStatus.OK
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(LogoutRequest logoutRequest) {
        return new ResponseEntity<>(
                authService.logout(logoutRequest),
                HttpStatus.OK
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<Jwt> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return new ResponseEntity<>(
                authService.refreshToken(refreshTokenRequest),
                HttpStatus.OK
        );
    }
}