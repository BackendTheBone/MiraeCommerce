package com.mirae.commerce.auth.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequest {
    private String username;
    private String password;
}
