package com.mirae.commerce.product.dto.response;

import com.mirae.commerce.image.dto.response.ProductImgInfo;
import com.mirae.commerce.image.entity.Image;
import com.mirae.commerce.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class GetProductResponse {
    private Long productId;
    private Long memberId;
    private Integer stock;
    private String name;
    private String detail;
    private Integer price;
    private LocalDateTime registeredAt;
    private LocalDateTime modifiedAt;
    private List<ProductImgInfo> imgInfoList;



    public static GetProductResponse from(Product product, List<Image> images){
        List<ProductImgInfo> newImages = images.stream().map((data) -> {
            return ProductImgInfo.builder()
                    .src(data.getSrc())
                    .sequence(data.getSequence())
                    .build();
        }).collect(Collectors.toList());


        return GetProductResponse.builder()
                .productId(product.getProductId())
                .memberId(product.getMemberId())
                .stock(product.getStock())
                .name(product.getName())
                .detail(product.getDetail())
                .price(product.getPrice())
                .registeredAt(product.getRegisteredAt())
                .modifiedAt(product.getModifiedAt())
                .imgInfoList(newImages)
                .build();
    }
}
