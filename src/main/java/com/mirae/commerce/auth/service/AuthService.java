package com.mirae.commerce.auth.service;

import com.mirae.commerce.auth.dto.RefreshTokenDto;
import com.mirae.commerce.auth.dto.LoginRequestDto;
import com.mirae.commerce.auth.dto.LogoutRequestDto;
import com.mirae.commerce.auth.jwt.Jwt;

public interface AuthService {
    Jwt login(LoginRequestDto loginRequestDto);
    boolean logout(LogoutRequestDto logoutRequestDto);

    Jwt refreshToken(RefreshTokenDto refreshTokenDto);
}
