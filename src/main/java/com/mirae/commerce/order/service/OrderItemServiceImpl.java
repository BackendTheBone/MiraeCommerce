package com.mirae.commerce.order.service;

import com.mirae.commerce.order.entity.OrderItem;
import com.mirae.commerce.order.repository.OrderItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderItemServiceImpl implements OrderItemService{
    private final OrderItemRepository orderItemRepository;

    public long addOrderItems(List<OrderItem> orderItems) {
        return add(orderItems);
    }

    private long add(List<OrderItem> orderItems){
        return orderItemRepository.saveAll(orderItems).size();
    }
}
