package com.mirae.commerce.order.service;

import com.mirae.commerce.order.dto.AddOrderRequest;

public interface OrderService {
    long addOrderRequest(AddOrderRequest addOrderRequest);
}
