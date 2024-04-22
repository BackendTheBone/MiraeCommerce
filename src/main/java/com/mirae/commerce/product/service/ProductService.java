package com.mirae.commerce.product.service;


import com.mirae.commerce.product.dto.request.AddProductRequest;
import com.mirae.commerce.product.dto.request.DeleteProductRequest;
import com.mirae.commerce.product.dto.request.ModifyProductRequest;
import com.mirae.commerce.product.dto.response.GetProductResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductService {
    @Transactional
    Long addProductOne(AddProductRequest addProductRequest);

    Long modifyProductOne(ModifyProductRequest modifyProductRequest);

    boolean removeProductOne(DeleteProductRequest deleteProductRequest);

    @Transactional(readOnly = true)
        // 성능 빠름
    GetProductResponse getProductOne(Long productId);

    @Transactional(readOnly = true)
    List<GetProductResponse> getProductListAll(Pageable pageable);

    @Transactional(readOnly = true)
    List<GetProductResponse> getUserProductList(Pageable pageable, String username);
}
