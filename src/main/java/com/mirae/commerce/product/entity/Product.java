package com.mirae.commerce.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table (name = "Product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;
    private Integer stock;
    private String name;

    @Lob
    private String detail;
    private Integer price;

    @CreatedDate // 요즘 많이 쓰는 추세
    @Column(updatable = false, nullable = false)
    private LocalDateTime registeredAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt;


    @Builder
    public Product(Long productId, Long memberId, Integer stock, String name, String detail, Integer price, LocalDateTime registeredAt, LocalDateTime modifiedAt) {
        this.id = productId;
        this.memberId = memberId;
        this.stock = stock;
        this.name = name;
        this.detail = detail;
        this.price = price;
        this.registeredAt = registeredAt;
        this.modifiedAt = modifiedAt;
    }

    public void updateProductData(Long productId, Integer stock, String name, String detail, Integer price){
        this.id = productId;
        this.stock = stock;
        this.name = name;
        this.detail = detail;
        this.price = price;
    }
}
