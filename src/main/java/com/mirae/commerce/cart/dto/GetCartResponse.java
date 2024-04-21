package com.mirae.commerce.cart.dto;

import com.mirae.commerce.cart.entity.CartItem;
import com.mirae.commerce.product.entity.Product;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetCartResponse {
    private long productId;
    private String name;
    private String url;
    private int price;
    private int stock;
    private int count;
    private long timestamp;
    
    public static GetCartResponse of(Product product, CartItem cartItem) {
    	return GetCartResponse.builder().
    			productId(product.getId()).
    			name(product.getName()).
    			price(product.getPrice()).
    			stock(product.getStock()).
    			count(cartItem.getCount()).
    			timestamp(cartItem.getTimestamp()).
    			build();
    }

}
