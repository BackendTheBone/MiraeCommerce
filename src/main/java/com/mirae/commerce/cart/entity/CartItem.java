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
<<<<<<< HEAD
	@Column
=======
	@Column(name="product_id")
>>>>>>> 16ab234bc0d75547ce0cc4cc29f59e685a4417a1
	private Long productId;
	@Column
	private Long timestamp;
	@Column
	private Integer count;
}