package com.mirae.commerce.cart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetUnauthenticatedCartRequest {
    private String cookie;
}
