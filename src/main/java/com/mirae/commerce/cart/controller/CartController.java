package com.mirae.commerce.cart.controller;

import com.mirae.commerce.cart.dto.*;
import com.mirae.commerce.cart.service.CartService;
import com.mirae.commerce.common.dto.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {
	private final CartService cartService;

	/* 로그인 사용자가 장바구니 조회 시 */
	@GetMapping("/authenticated")
	public ResponseEntity<List<GetCartResponse>> getCarts(@RequestBody GetAuthenticatedCartsRequest getAuthenticatedCartRequest){
		List<GetCartResponse> carts = cartService.getCartsRequest(getAuthenticatedCartRequest);
		return new ResponseEntity<>(carts, HttpStatus.OK);
	}

	/* 비로그인 사용자가 장바구니 조회 시 */
	@GetMapping("/unauthenticated")
	public ResponseEntity<List<GetCartResponse>> getCarts(@RequestBody GetUnauthenticatedCartsRequest getUnauthenticatedCartRequest) {
		List<GetCartResponse> carts = cartService.getCartsRequest(getUnauthenticatedCartRequest);
		return new ResponseEntity<>(carts, HttpStatus.OK);
	}
	
	/* 로그인 사용자가 장바구니 추가 시 (비 로그인의 경우 프론트에서 쿠키로 조작) */
	@PostMapping
	public ResponseEntity<ApiResponse> addCart(@RequestBody AddCartRequest addCartRequest){
		cartService.addCartRequest(addCartRequest);
		return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "장바구니 요청 아이템 추가 성공"), HttpStatus.ACCEPTED);
	}
	
	/* 로그인 사용자가 장바구니 다중 품목 삭제 시*/
	@DeleteMapping
	public ResponseEntity<ApiResponse> deleteCart(@RequestBody DeleteCartsRequest deleteCartsRequest){
		long attemptedCount = deleteCartsRequest.getProductIds().size();
		long successCount = cartService.deleteCartsRequest(deleteCartsRequest);
		if(successCount == attemptedCount)
			return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "장바구니 모든 요청 아이템 삭제 성공"), HttpStatus.ACCEPTED);
		else if(successCount == 0)
			return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, "일치하는 정보가 없음"), HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, String.format("장바구니 요청 아이템 중 %d개 삭제 성공", successCount)), HttpStatus.MULTI_STATUS);
	}
}