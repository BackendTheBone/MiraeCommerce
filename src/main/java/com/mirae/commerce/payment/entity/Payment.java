package com.mirae.commerce.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.io.support.SpringFactoriesLoader;

@Table
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(name="order_id")
    Long orderId;
    @Column
    Integer amount;
    @Column
    String status;

    public void changeStatus(String status){
        this.status = status;
    }
}
