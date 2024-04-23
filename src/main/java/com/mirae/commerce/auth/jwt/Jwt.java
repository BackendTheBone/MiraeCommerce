package com.mirae.commerce.auth.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Jwt {
    private String accessToken;
    private String refreshToken;
}
