package com.mirae.commerce.cart.dto;

import com.mirae.commerce.cart.entity.CartItem;
import com.mirae.commerce.product.entity.Product;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetCartItemResponse {
    private long productId;
    private String name;
    private List<String> urls;
    private int price;
    private int stock;
    private int count;
    private long timestamp;
    
    public static GetCartItemResponse of(Product product, CartItem cartItem) {
    	return GetCartItemResponse.builder().
    			productId(product.getId()).
    			name(product.getName()).
    			price(product.getPrice()).
    			stock(product.getStock()).
    			count(cartItem.getCount()).
    			timestamp(cartItem.getTimestamp()).
    			build();
    }

}
