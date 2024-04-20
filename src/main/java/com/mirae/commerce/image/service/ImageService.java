package com.mirae.commerce.image.service;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    List<String> uploadFiles(List<MultipartFile> multipartFileList, Long productId);

    void removeS3Img(String deleteSrc);
    @Transactional
    ResponseEntity<?> deleteProductAllImage(Long productId);
}
