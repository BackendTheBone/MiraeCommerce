package com.mirae.commerce.order.service;

import com.mirae.commerce.order.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    long addOrderItems(List<OrderItem> orderItems);
}
