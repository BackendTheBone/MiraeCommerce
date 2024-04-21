package com.mirae.commerce.cart.repository;

import com.mirae.commerce.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
	long deleteAllByUsernameAndCartItemProductIdIn(String username, List<Long> productIds);
	List<Cart> findAllByUsername(String username);
	List<Cart> findAllByUsernameAndCartItemProductIdIn(String username, List<Long> productIds);
}
