package com.mirae.commerce.cart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Table
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart{
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private Long memberId;
	@Embedded
	private CartItem cartItem;

	public void modifyId(Long id) {
		this.id = id;
	}

	public Long getProductId() { return cartItem.getProductId(); }

	public Long getTimestamp() {
		return cartItem.getTimestamp();
	}
	
	public Integer getCount() {
		return cartItem.getCount();
	}
	
}
