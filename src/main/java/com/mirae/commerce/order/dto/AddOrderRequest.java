package com.mirae.commerce.order.dto;

import com.mirae.commerce.order.vo.OrderItemVO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AddOrderRequest {
    String username;
    String address;
    List<OrderItemVO> orderItemVOs;
}