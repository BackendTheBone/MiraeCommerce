package com.mirae.commerce.auth.jwt;

public interface RefreshTokenManager {
    void putRefreshToken(String username, String refreshToken);
    String getRefreshToken(String username);
    void removeRefreshToken(String username);
}