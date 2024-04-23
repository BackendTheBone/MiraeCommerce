package com.mirae.commerce.cart.dto;

import com.mirae.commerce.cart.entity.Cart;
import com.mirae.commerce.cart.entity.CartItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddCartRequest {
	private String username;
	private long productId;
	private long timestamp;
	private int count;
	
	public Cart toEntity() {
		return Cart.builder().
				username(this.username).
				cartItem(new CartItem(productId, timestamp, count)).
				build();
	}
}

