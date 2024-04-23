package com.mirae.commerce.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name="`order`")
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
    @Column
    Long memberId;
    @Column
    Long paymentId;
    @Column
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
