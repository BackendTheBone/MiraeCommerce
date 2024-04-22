package com.mirae.commerce.order.vo;

import com.mirae.commerce.order.entity.Order;
import com.mirae.commerce.order.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderItemVO {
    long productId;
    int count;
    int price;

    public OrderItem toEntity(Order order){
        return OrderItem.builder().
                orderId(order.getId()).
                productId(this.productId).
                count(this.count).
                price(this.price).build();
    }
}
