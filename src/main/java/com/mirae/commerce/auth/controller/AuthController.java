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
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    // TODO : 추후 적절한 상수 관리법을 찾아 그곳에 위치시키기
    private static final String ACCESS_TOKEN_COOKIE_KEY = "accessToken";
    private static final String REFRESH_TOKEN_COOKIE_KEY = "refreshToken";
    private static final long DEFAULT_ACCESS_TOKEN_COOKIE_MAX_AGE = 1800L;
    private static final long DEFAULT_REFRESH_TOKEN_COOKIE_MAX_AGE = 60L * 60 * 24 * 30;

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody LoginRequest loginRequest) {
        return buildJwtResponse(
                authService.login(loginRequest),
                DEFAULT_ACCESS_TOKEN_COOKIE_MAX_AGE,
                DEFAULT_REFRESH_TOKEN_COOKIE_MAX_AGE
        );
    }

    @JwtRequired
    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(@JwtUsernameInject LogoutRequest logoutRequest) {
        authService.logout(logoutRequest);
        return buildJwtResponse(new Jwt("", ""), 0L, 0L);
    }

    @JwtRequired
    @PostMapping("/refresh-token")
    public ResponseEntity<Boolean> refreshToken(@JwtUsernameInject @RefreshTokenInject RefreshTokenRequest refreshTokenRequest) {
        return buildJwtResponse(
                authService.refreshToken(refreshTokenRequest),
                DEFAULT_ACCESS_TOKEN_COOKIE_MAX_AGE,
                DEFAULT_REFRESH_TOKEN_COOKIE_MAX_AGE
        );
    }

    private ResponseEntity<Boolean> buildJwtResponse(Jwt jwt, long accessTokenMaxAge, long refreshTokenMaxAge) {
        ResponseCookie accessTokenCookie = ResponseCookie.from(ACCESS_TOKEN_COOKIE_KEY, jwt.getAccessToken())
                .httpOnly(true)
                .path("/")
                .maxAge(accessTokenMaxAge)
                .build();
        ResponseCookie refreshTokenCookie = ResponseCookie.from(REFRESH_TOKEN_COOKIE_KEY, jwt.getRefreshToken())
                .httpOnly(true)
                .path("/api/auth/refresh-token")
                .maxAge(refreshTokenMaxAge)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(true);
    }
}