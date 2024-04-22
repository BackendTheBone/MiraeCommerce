package com.mirae.commerce.product.dto.request;

import jakarta.persistence.Lob;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ModifyProductRequest {
    private Long productId;
    private Integer stock;
    private String name;
    @Lob
    private String detail;
    private Integer price;

   //삭제된 sequence번호들
    private List<Long> removeImgSequenceList;

    //추가할 img들
    private List<MultipartFile> addProductImgList;


}
