package com.mirae.commerce.cart.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
	@Column
	private Long productId;
	@Column
	private Long timestamp;
	@Column
	private Integer count;
}