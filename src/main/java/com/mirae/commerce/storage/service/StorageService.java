package com.mirae.commerce.storage.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {
    List<String> saveImages(List<MultipartFile> multipartFileList, Long productId);
    void removeImages(String deleteSrc);
    //그 상품 삭제시 상품과 연관된 이미지 삭제
    ResponseEntity<?> removeProductAllImage(Long productId);

}
