package com.mirae.commerce.payment.service;

import com.mirae.commerce.order.entity.Order;
import com.mirae.commerce.payment.entity.Payment;
import com.mirae.commerce.payment.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    public Payment requestPayment(Order order, int amount) {
        // TODO. ENUM 바꾸기
        Payment payment = Payment.builder().orderId(order.getId()).amount(amount).status("0").build();
        boolean isSuccess = requestApprove(payment);
        if(isSuccess)
            payment.changeStatus("1");
        else
            payment.changeStatus("2");
        return add(payment);
    }

    private Payment add(Payment payment){
        return paymentRepository.save(payment);
    }

    // 외부 모듈이라 가정, 1% 로 실패
    private boolean requestApprove(Payment payment) {
        return !(Math.random() < 0.01);
    }
}
