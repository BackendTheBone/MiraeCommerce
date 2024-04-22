package com.mirae.commerce.product.service;


import com.mirae.commerce.common.exception.NotFoundException;
import com.mirae.commerce.image.entity.Image;
import com.mirae.commerce.image.repository.ImageRepository;
import com.mirae.commerce.image.service.ImageService;
import com.mirae.commerce.product.dto.request.AddProductRequest;
import com.mirae.commerce.product.dto.request.DeleteProductRequest;
import com.mirae.commerce.product.dto.request.ModifyProductRequest;
import com.mirae.commerce.product.dto.response.GetProductResponse;
import com.mirae.commerce.product.entity.Product;
import com.mirae.commerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;

    @Override
    @Transactional
    public Long addProductOne(AddProductRequest addProductRequest){
        //상품 추가
        Product productEntity = addProductRequest.toProductEntity();
        Product createdEntity = add(productEntity);

        //이미지 추가
        if(addProductRequest.getMultipartFileList() != null){
            imageService.uploadFiles(addProductRequest.getMultipartFileList(), createdEntity.getProductId());
        }

        return createdEntity.getProductId();
    }

    @Override
    public Long modifyProductOne(ModifyProductRequest modifyProductRequest){
        Product repositoryProduct = productRepository.findById(modifyProductRequest.getProductId())
                .orElseThrow(() -> {
                    throw new NotFoundException("product not exist");
                });

        //dto넣고 함수 더럽히기
//        repositoryProduct.updateProductData(modifyProductRequest.getProductId(), modifyProductRequest.getStock(), modifyProductRequest.getName(), modifyProductRequest.getDetail(), modifyProductRequest.getPrice());
        repositoryProduct.updateProductData(modifyProductRequest);

        //이미지 수정

        //이미지 삭제
        if(modifyProductRequest.getRemoveImgSequenceList() != null){
            for(int i=0;i<modifyProductRequest.getRemoveImgSequenceList().size();i++){
                Long sequence = modifyProductRequest.getRemoveImgSequenceList().get(i);
                String sequenceSrc = imageRepository.findSrcBySequence(sequence);

                imageService.removeS3Img(sequenceSrc);
                imageRepository.deleteById(sequence);
            }
        }

        //이미지 추가
        if(modifyProductRequest.getAddProductImgList() != null){
            imageService.uploadFiles(modifyProductRequest.getAddProductImgList(), modifyProductRequest.getProductId());
        }

        return productRepository.save(repositoryProduct).getProductId();
    }


    @Override
    @Transactional
    public boolean removeProductOne(DeleteProductRequest deleteProductRequest){
        imageService.deleteProductAllImage(deleteProductRequest.getProductId());
        productRepository.deleteById(deleteProductRequest.getProductId());
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public GetProductResponse getProductOne(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    throw new RuntimeException("product not exist");
                });

        List<Image> images = imageRepository.findByProductId(productId);

        return GetProductResponse.from(product, images);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetProductResponse> getProductListAll(Pageable pageable){
        return productRepository.findByOrderByRegisteredAt(pageable)
                .stream().map((data) -> {
                    return GetProductResponse.builder()
                            .productId(data.getProductId())
                            .memberId(data.getMemberId())
                            .stock(data.getStock())
                            .name(data.getName())
                            .detail(data.getDetail())
                            .price(data.getPrice())
                            .modifiedAt(data.getModifiedAt())
                            .registeredAt(data.getRegisteredAt())
                            .build();
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetProductResponse> getUserProductList(Pageable pageable, String username){
        return productRepository.findProductListByUsername(pageable, username)
                .stream().map((data) -> {
                    return GetProductResponse.builder()
                            .productId(data.getProductId())
                            .memberId(data.getMemberId())
                            .stock(data.getStock())
                            .name(data.getName())
                            .detail(data.getDetail())
                            .price(data.getPrice())
                            .modifiedAt(data.getModifiedAt())
                            .registeredAt(data.getRegisteredAt())
                            .build();
                }).collect(Collectors.toList());

    }








    private Product add(Product product){
        return productRepository.save(product);
    }


}
