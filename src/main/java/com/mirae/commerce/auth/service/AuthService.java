package com.mirae.commerce.auth.service;

import com.mirae.commerce.auth.dto.RefreshTokenRequest;
import com.mirae.commerce.auth.dto.LoginRequest;
import com.mirae.commerce.auth.dto.LogoutRequest;
import com.mirae.commerce.auth.jwt.Jwt;

public interface AuthService {
    Jwt login(LoginRequest loginRequest);
    boolean logout(LogoutRequest logoutRequest);

    Jwt refreshToken(RefreshTokenRequest refreshTokenRequest);
}
