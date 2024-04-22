package com.mirae.commerce.order.controller;

import com.mirae.commerce.common.dto.ApiResponse;
import com.mirae.commerce.order.dto.AddOrderRequest;
import com.mirae.commerce.order.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse> addOrderRequest(@RequestBody AddOrderRequest addOrderRequest) {
        long successCount = orderService.addOrderRequest(addOrderRequest);
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, String.format("품목 %d개 주문 성공", successCount)), HttpStatus.ACCEPTED);
    }

}
