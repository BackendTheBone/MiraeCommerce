package com.mirae.commerce.product.controller;


import com.mirae.commerce.image.service.ImageService;
import com.mirae.commerce.product.dto.request.AddProductRequest;
import com.mirae.commerce.product.dto.request.DeleteProductRequest;
import com.mirae.commerce.product.dto.request.ModifyProductRequest;
import com.mirae.commerce.product.dto.response.GetProductResponse;
import com.mirae.commerce.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    //상품 등록
    // TODO. Username 으로 받아야됨, 이걸로 member id 찾기

    @PostMapping()
    public ResponseEntity<?> addProduct(@ModelAttribute AddProductRequest addProductRequest){
        Long createdProductId = productService.addProductOne(addProductRequest);

        return ResponseEntity.created(URI.create("/products/" + createdProductId)).build();
    }


    //상품 수정
    @PatchMapping()
    public ResponseEntity<?> modifyProduct(@ModelAttribute ModifyProductRequest modifyProductRequest){
        Long modifyProductId = productService.modifyProductOne(modifyProductRequest);

        return ResponseEntity.ok(URI.create("/products/" + modifyProductId));
    }

    //상품 삭제
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> removeProduct(@PathVariable("productId") Long productId){
        //적합한 회원이 보냈는지 검사
        DeleteProductRequest deleteProductRequest = DeleteProductRequest.builder().productId(productId).build();
        productService.removeProductOne(deleteProductRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //상품 단 건 조회
    @GetMapping("/{productId}")
    public ResponseEntity<GetProductResponse> showProduct(@PathVariable("productId") Long productId){
        GetProductResponse getProductOne = productService.getProductOne(productId);

        return new ResponseEntity<>(getProductOne, HttpStatus.OK);
    }


    //상품 페이지 조회
    @GetMapping("/getList")
    public ResponseEntity<List<GetProductResponse>> showProductList(Pageable pageable){
        List<GetProductResponse> list = productService.getProductListAll(pageable);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    //판매자가 자기자신이 올린 상품들 조회
    @GetMapping("/getList/{username}")
    public ResponseEntity<List<GetProductResponse>> showMyProductList(@PathVariable("username") String username, Pageable pageable){
        List<GetProductResponse> list = productService.getUserProductList(pageable, username);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }



    //S3
    // 이미지
    // TODO. 삭제
    private final ImageService imageService;

    @PostMapping("/imageUpload")
    public ResponseEntity<List<String>> uploadFiles(@RequestBody List<MultipartFile> multipartFileList, Long productId){

        return ResponseEntity.ok(imageService.uploadFiles(multipartFileList, productId));
    }


    @DeleteMapping("/images/{productId}")
    public ResponseEntity<?> deleteAllImages(@PathVariable("productId") Long productId){
        return imageService.deleteProductAllImage(productId);
    }


}
