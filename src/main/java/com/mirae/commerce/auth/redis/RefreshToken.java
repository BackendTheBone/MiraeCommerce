package com.mirae.commerce.auth.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 3600)
@AllArgsConstructor
public class RefreshToken {

    @Id
    private String username;

    private String refreshToken;
}