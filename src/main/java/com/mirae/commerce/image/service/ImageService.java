package com.mirae.commerce.image.service;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    String uploadFiles(MultipartFile multipartFileList, String locationUrl);

    void removeS3Img(String deleteSrc);
    @Transactional
    ResponseEntity<?> deleteProductAllImage(Long productId);
}
