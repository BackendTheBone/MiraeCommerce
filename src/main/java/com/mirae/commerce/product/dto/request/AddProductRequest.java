package com.mirae.commerce.product.dto.request;

import com.mirae.commerce.product.entity.Product;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter // multipart타입을 파라미터로 받아서 쓸라고 했는데 안됨
@Builder
public class AddProductRequest {
    private Long memberId;
    private Integer stock;
    private String name;
    private String detail;
    private Integer price;
    private List<MultipartFile> multipartFileList;

    public Product toProductEntity(){
        Product product = Product.builder()
                .memberId(this.memberId)
                .stock(this.stock)
                .name(this.name)
                .detail(this.detail)
                .price(this.price)
                .build();

        return product;
    }

}
