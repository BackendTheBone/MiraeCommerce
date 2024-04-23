package com.mirae.commerce.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.mirae.commerce.common.exception.NotFoundException;
import com.mirae.commerce.image.dto.request.AddProductImageRequest;
import com.mirae.commerce.image.dto.request.DeleteS3ProductAllImageRequest;
import com.mirae.commerce.image.entity.Image;
import com.mirae.commerce.image.repository.ImageRepository;
import com.mirae.commerce.product.entity.Product;
import com.mirae.commerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    @Override
    public String uploadFiles(MultipartFile multipartFile, String locationUrl){

            // 파일 이름을 UUID로 섞어서 사용할 예정
            String fileName = createFileName(multipartFile.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(multipartFile.getSize());
            objectMetadata.setContentType(multipartFile.getContentType());

            String imageUrl = "";
            try(InputStream inputStream = multipartFile.getInputStream()){
                amazonS3.putObject(new PutObjectRequest(locationUrl, fileName, inputStream, objectMetadata));
                // ex) url : https://ssafy11mirae.s3.ap-northeast-2.amazonaws.com/2024/413/5/658beefc-a591-4e6c-8339-b3ea3ed497bc.jpg
                imageUrl = amazonS3.getUrl(locationUrl, fileName).toString();
            } catch(IOException e){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일업로드 실패");
            }


        return imageUrl;
    }

    @Override
    public void removeS3Img(String deleteSrc){
        String pattern = "(?<=" + bucket + "\\/).*";
        Pattern r = Pattern.compile(pattern);

        // 매칭
        Matcher m = r.matcher(deleteSrc);
        String extractedString = "";
        if (m.find()) {
            extractedString = m.group(0);
            System.out.println("Extracted string: " + extractedString);
        } else {
            System.out.println("No match found.");
        }

        amazonS3.deleteObject(new DeleteObjectRequest(bucket, extractedString));
    }

    //productId 삭제할때 이미지 다 삭제 DB
    @Override
    @Transactional
    public ResponseEntity<?> deleteProductAllImage(Long productId) {
        imageRepository.deleteAllByProduct(productId);
        DeleteS3ProductAllImageRequest deleteS3ProductAllImageRequest = DeleteS3ProductAllImageRequest.builder()
                        .productId(productId)
                        .build();
        removeS3ProductImages(deleteS3ProductAllImageRequest);

        return ResponseEntity.ok("removeOK");
    }


    //파일 업로드시
    private String createFileName(String fileName){
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }
    //확장자명 빼기

    //파일 타입과 상관없이 업로드할 수 있게 하기위해, "."의 존재 유무만 판단
    private String getFileExtension(String fileName){
        try{
            return fileName.substring(fileName.lastIndexOf("."));
        } catch(StringIndexOutOfBoundsException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 파일 형식");
        }
    }

    //S3 productId랑 관련된 애들 다 삭제
    private void removeS3ProductImages(DeleteS3ProductAllImageRequest deleteS3ProductAllImageRequest){
//        productId로 Product엔티티 가져와서 S3에 있는 디렉토리 지우기
        Product product = productRepository.findById(deleteS3ProductAllImageRequest.getProductId())
                .orElseThrow(() -> {
                    throw new NotFoundException("product not exist");
                });

        LocalDate productDate = product.getRegisteredAt().toLocalDate();
        int year = productDate.getYear();
        int month = productDate.getMonthValue();
        int day = productDate.getDayOfMonth();

        StringBuilder productURL = new StringBuilder();
        productURL.append(year).append("/").append(month).append(day).append("/").append(deleteS3ProductAllImageRequest.getProductId());

        deleteS3Directory(bucket, productURL.toString());
    }

    //이미지 삭제하고 디렉토리까지 지우기
    private void deleteS3Directory(String bucketName, String directoryName) {
        List<S3ObjectSummary> objects = amazonS3.listObjects(bucketName, directoryName).getObjectSummaries();

        if (!objects.isEmpty()) {
            // 디렉토리에 속한 객체들 삭제
            DeleteObjectsRequest deleteRequest = new DeleteObjectsRequest(bucketName)
                    .withKeys(objects.stream().map(S3ObjectSummary::getKey).toArray(String[]::new));
            amazonS3.deleteObjects(deleteRequest);
        }
        // 디렉토리 삭제
        amazonS3.deleteObject(bucketName, directoryName);
    }
}
