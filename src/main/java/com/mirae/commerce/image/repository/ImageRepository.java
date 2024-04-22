package com.mirae.commerce.image.repository;

import com.mirae.commerce.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("delete from Image i where i.productId = :productId")
    @Modifying
    void deleteAllByProduct(@Param("productId") Long productId);

    List<Image> findByProductId(Long productId);

    @Query("select i.src from Image i where i.sequence = :sequence")
    String findSrcBySequence(@Param("sequence") Long sequence);

}
