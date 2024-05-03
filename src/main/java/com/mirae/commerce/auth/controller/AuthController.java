package com.mirae.commerce.auth.controller;

import com.mirae.commerce.auth.dto.RefreshTokenRequest;
import com.mirae.commerce.auth.dto.LogoutRequest;
import com.mirae.commerce.auth.jwt.Jwt;
import com.mirae.commerce.auth.jwt.JwtRequired;
import com.mirae.commerce.auth.jwt.JwtUsernameInject;
import com.mirae.commerce.auth.jwt.RefreshTokenInject;
import com.mirae.commerce.auth.service.AuthService;
import com.mirae.commerce.auth.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Jwt jwt = authService.login(loginRequest);
        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", jwt.getAccessToken())
                .httpOnly(true)
                .path("/")
                .build();
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", jwt.getRefreshToken())
                .httpOnly(true)
                .path("/api/auth/refresh-token")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(true);
    }

    @JwtRequired
    @PostMapping("/auth/logout")
    public ResponseEntity<Boolean> logout(@JwtUsernameInject LogoutRequest logoutRequest) {
        authService.logout(logoutRequest);
        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .build();
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .path("/api/auth/refresh-token")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(true);
    }

    @JwtRequired
    @PostMapping("/auth/refresh-token")
    public ResponseEntity<Boolean> refreshToken(@JwtUsernameInject @RefreshTokenInject RefreshTokenRequest refreshTokenRequest) {
        Jwt jwt = authService.refreshToken(refreshTokenRequest);

        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", jwt.getAccessToken())
                .httpOnly(true)
                .path("/")
                .build();
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", jwt.getRefreshToken())
                .httpOnly(true)
                .path("/api/auth/refresh-token")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(true);
    }
}