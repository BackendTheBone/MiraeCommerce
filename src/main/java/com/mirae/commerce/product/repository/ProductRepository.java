package com.mirae.commerce.product.repository;

import com.mirae.commerce.product.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByOrderByRegisteredAt(Pageable pageable);
//    List<Product> findProductByMemberIdOrderByRegisteredAt(Pageable pageable);

    @Query("select p from Product p where p.memberId = (" +
<<<<<<< HEAD
            "select m.memberId from Member m where m.username = :username)")
    List<Product> findProductListByUsername(Pageable pageable, @Param("username") String username);

    List<Product> findByIdIn(List<Long> productIds);
=======
            "select m.id from Member m where m.username = :username)")
    List<Product> findProductListByUsername(Pageable pageable, @Param("username") String username);

    List<Product> findByProductIdIn(List<Long> ids);

>>>>>>> 16ab234bc0d75547ce0cc4cc29f59e685a4417a1
}

