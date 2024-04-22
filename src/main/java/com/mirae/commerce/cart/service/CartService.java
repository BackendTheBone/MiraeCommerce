package com.mirae.commerce.cart.service;

import com.mirae.commerce.cart.dto.*;

import java.util.List;

public interface CartService {
    long addCarts(String username, String cookie);

    long deleteCart(String username, long productId);

    long deleteCarts(String username, List<Long> productIds);

    List<GetCartResponse> getCartsRequest(GetAuthenticatedCartsRequest getAuthenticatedCartRequest);

    List<GetCartResponse> getCartsRequest(GetUnauthenticatedCartsRequest getUnauthenticatedCartRequest);

    long addCartRequest(AddCartRequest addCartRequest);

    long deleteCartsRequest(DeleteCartsRequest deleteCartsRequest);
}
