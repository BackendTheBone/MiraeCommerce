package com.mirae.commerce.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(name="member_id")
    Long memberId;
    @Column(name="payment_id")
    Long paymentId;
    @Column(name="ordered_at")
    LocalDateTime orderedAt;
    @Column
    String address;
    @Column
    String status;

    public void linkPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public void changeStatus(String status){
        this.status = status;
    }
}
