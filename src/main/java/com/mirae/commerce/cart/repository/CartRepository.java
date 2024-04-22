package com.mirae.commerce.cart.repository;

import com.mirae.commerce.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
	long deleteAllByMemberIdAndCartItemProductIdIn(long MemberId, List<Long> productIds);
	List<Cart> findByMemberId(long MemberId);
	List<Cart> findByMemberIdAndCartItemProductIdIn(long MemberId, List<Long> productIds);
}
