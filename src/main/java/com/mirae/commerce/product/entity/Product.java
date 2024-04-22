package com.mirae.commerce.product.entity;


import com.mirae.commerce.product.dto.request.ModifyProductRequest;
import com.mirae.commerce.product.dto.response.GetProductResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table (name = "Product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//Audi라는게 감시하다라는 뜻이며 시간을 자동으로 값을 넣어줌
@EntityListeners(value = {AuditingEntityListener.class})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincreasedment
    private Long productId;

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
        this.productId = productId;
        this.memberId = memberId;
        this.stock = stock;
        this.name = name;
        this.detail = detail;
        this.price = price;
        this.registeredAt = registeredAt;
        this.modifiedAt = modifiedAt;
    }

    public void updateProductData(Long productId, Integer stock, String name, String detail, Integer price){
        this.productId = productId;
        if(stock != null){
            this.stock = stock;
        }
        if(name != null){
            this.name = name;
        }
        if(detail != null){
            this.detail = detail;
        }
        if(price != null) {
            this.price = price;
        }
    }

    public void updateProductData(ModifyProductRequest modifyProductRequest){
        this.productId = modifyProductRequest.getProductId();
        if(modifyProductRequest.getStock() != null){
            this.stock = modifyProductRequest.getStock();
        }
        if(modifyProductRequest.getName() != null){
            this.name = modifyProductRequest.getName();
        }
        if(modifyProductRequest.getDetail() != null){
            this.detail = modifyProductRequest.getDetail();
        }
        if(modifyProductRequest.getPrice() != null) {
            this.price = modifyProductRequest.getPrice();
        }
    }

    public static GetProductResponse from(Product product){
        return GetProductResponse.builder()
                .productId(product.getProductId())
                .memberId(product.getMemberId())
                .stock(product.getStock())
                .name(product.getName())
                .detail(product.getDetail())
                .price(product.getPrice())
                .registeredAt(product.getRegisteredAt())
                .modifiedAt(product.getModifiedAt())
                .build();
    }

}
