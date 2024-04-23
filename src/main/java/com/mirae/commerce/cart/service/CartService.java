package com.mirae.commerce.cart.service;

import com.mirae.commerce.cart.dto.*;

import java.util.List;

public interface CartService {
    long addCartItems(long memberId, String cookie);

    long deleteCartItem(long memberId, long productId);

    long deleteCartItems(long memberId, List<Long> productIds);

    List<GetCartItemResponse> getCartRequest(GetAuthenticatedCartRequest getAuthenticatedCartRequest);

    List<GetCartItemResponse> getCartRequest(GetUnauthenticatedCartRequest getUnauthenticatedCartRequest);

    long addCartItemRequest(AddCartItemRequest addCartItemRequest);

    long deleteCartItemsRequest(DeleteCartItemsRequest deleteCartItemsRequest);
}
