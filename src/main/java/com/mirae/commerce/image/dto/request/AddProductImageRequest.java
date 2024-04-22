package com.mirae.commerce.image.dto.request;

import com.mirae.commerce.image.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AddProductImageRequest {
    private Long productId;
    private String src;

    public Image toEntity(){
        Image image = Image.builder()
                .productId(this.productId)
                .src(this.src)
                .build();

        return image;
    }

}
