package com.mirae.commerce.cart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetAuthenticatedCartsRequest {
    private String username;
}
