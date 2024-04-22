package com.mirae.commerce.auth.redis;

import com.mirae.commerce.auth.jwt.RefreshTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisRefreshTokenManager implements RefreshTokenManager {
    private final RedisRefreshTokenRepository redisRefreshTokenRepository;

    @Override
    public void putRefreshToken(String username, String refreshToken) {
        redisRefreshTokenRepository.save(new RefreshToken(refreshToken, username));
    }

    @Override
    public String getRefreshToken(String username) {
        return redisRefreshTokenRepository.findById(username).get().getRefreshToken();
    }

    @Override
    public void removeRefreshToken(String username) {
        redisRefreshTokenRepository.deleteById(username);
    }
}