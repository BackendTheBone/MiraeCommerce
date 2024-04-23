package com.mirae.commerce.payment.service;

import com.mirae.commerce.order.entity.Order;
import com.mirae.commerce.payment.entity.Payment;

public interface PaymentService {
    Payment requestPayment(Order order, int totalPrice);
}
