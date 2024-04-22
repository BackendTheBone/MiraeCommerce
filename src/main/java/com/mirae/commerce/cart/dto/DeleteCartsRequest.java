package com.mirae.commerce.cart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class DeleteCartsRequest {
	private String username;
	private List<Long> productIds;
}
