package com.mirae.commerce.auth.controller;

import com.mirae.commerce.auth.dto.RefreshTokenRequest;
import com.mirae.commerce.auth.dto.LogoutRequest;
import com.mirae.commerce.auth.jwt.Jwt;
import com.mirae.commerce.auth.jwt.JwtRequired;
import com.mirae.commerce.auth.service.AuthService;
import com.mirae.commerce.auth.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<Jwt> login(LoginRequest loginRequest) {
        return ResponseEntity.ok()
                // TODO : 쿠키 생성 적용하기
                // .header(headerName, headerValue)
                .body(authService.login(loginRequest));
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<Boolean> logout(LogoutRequest logoutRequest) {
        return ResponseEntity.ok()
                // TODO : 쿠키 삭제 적용하기
                // .header(headerName, headerValue)
                .body(authService.logout(logoutRequest));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<Jwt> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok()
                .body(authService.refreshToken(refreshTokenRequest));
    }
}