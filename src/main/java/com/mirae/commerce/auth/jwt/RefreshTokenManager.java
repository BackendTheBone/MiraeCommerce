package com.mirae.commerce.auth.jwt;

public interface RefreshTokenManager {
    void putRefreshToken(String username, String uuid);
    String getRefreshToken(String username);
    void removeRefreshToken(String username);
}