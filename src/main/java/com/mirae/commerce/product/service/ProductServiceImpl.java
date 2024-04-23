package com.mirae.commerce.product.service;


import com.mirae.commerce.common.exception.NotFoundException;
import com.mirae.commerce.image.dto.request.AddProductImageRequest;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;

    @Override
    @Transactional
    public Long addProductOne(AddProductRequest addProductRequest){
        //상품 추가
        Product productEntity = addProductRequest.toEntity();
        Product createdEntity = add(productEntity);

        //이미지 추가
        if(addProductRequest.getMultipartFileList() != null){
            String locationUrl = makeURL(createdEntity.getId());
            addProductRequest.getMultipartFileList().forEach((img) -> {
                String imageUrl = imageService.uploadFiles(img, locationUrl);

                AddProductImageRequest addImage = AddProductImageRequest.builder()
                        .productId(createdEntity.getId())
                        .src(imageUrl)
                        .build();
                Image imageEntity = addImage.toEntity();
                add(imageEntity);
            });
        }

        return createdEntity.getId();
    }

    @Override
    public Long modifyProductOne(ModifyProductRequest modifyProductRequest){
        Product repositoryProduct = productRepository.findById(modifyProductRequest.getProductId())
                .orElseThrow(() -> {
                    throw new NotFoundException("product not exist");
                });

        //이거 public static으로 선언해서 entity클래스 안에서 메서드를 만드는 것이 아닌
        // dto를 통해서 수정하도록 만들기
//        repositoryProduct.updateProductData(modifyProductRequest);

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
            String locationURL = makeURL(modifyProductRequest.getProductId());

            modifyProductRequest.getAddProductImgList().forEach((img) -> {
                String imageUrl = imageService.uploadFiles(img, locationURL);

                AddProductImageRequest addImage = AddProductImageRequest.builder()
                        .productId(modifyProductRequest.getProductId())
                        .src(imageUrl)
                        .build();
                Image imageEntity = addImage.toEntity();
                add(imageEntity);
            });
        }

        return productRepository.save(repositoryProduct).getId();
    }


    @Override
    public boolean removeProductOne(DeleteProductRequest deleteProductRequest){
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
                            .productId(data.getId())
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
                            .productId(data.getId())
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
    private String makeURL(Long id){
        //ex) 2015/0501/2/sequence
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();
        StringBuilder location = new StringBuilder();
        location.append(bucket).append("/").append(year).append("/").append(month).append(day).append("/").append(id);

        return location.toString();
    }
    private Image add(Image image){
        return imageRepository.save(image);
    }

}
