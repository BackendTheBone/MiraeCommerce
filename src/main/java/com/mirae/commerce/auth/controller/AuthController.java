package com.mirae.commerce.auth.controller;

import com.mirae.commerce.auth.dto.RefreshTokenDto;
import com.mirae.commerce.auth.dto.LogoutRequestDto;
import com.mirae.commerce.auth.jwt.Jwt;
import com.mirae.commerce.auth.service.AuthService;
import com.mirae.commerce.mail.service.MailService;
import com.mirae.commerce.auth.dto.LoginRequestDto;
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
    private final MailService mailService;

    @PostMapping("/login")
    public ResponseEntity<Jwt> login(LoginRequestDto loginRequestDto) {
        return new ResponseEntity<>(
                authService.login(loginRequestDto),
                HttpStatus.OK
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(LogoutRequestDto logoutRequestDto) {
        return new ResponseEntity<>(
                authService.logout(logoutRequestDto),
                HttpStatus.OK
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<Jwt> refreshToken(RefreshTokenDto refreshTokenDto) {
        return new ResponseEntity<>(
                authService.refreshToken(refreshTokenDto),
                HttpStatus.OK
        );
    }
}