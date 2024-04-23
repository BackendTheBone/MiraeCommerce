package com.mirae.commerce.cart.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mirae.commerce.cart.dto.*;
import com.mirae.commerce.cart.entity.Cart;
import com.mirae.commerce.cart.entity.CartItem;
import com.mirae.commerce.cart.repository.CartRepository;
import com.mirae.commerce.common.utils.ListUtil;
import com.mirae.commerce.member.repository.MemberRepository;
import com.mirae.commerce.product.entity.Product;
import com.mirae.commerce.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {
	private final CartRepository cartRepository;
	private final MemberRepository memberRepository;
	private final ProductRepository productRepository;

	@Override
	public long addCartItems(long memberId, String cookie) {
		List<Cart> carts = new ArrayList<>();
		List<CartItem> cartItems = getCartItems(cookie);
		for (CartItem cartItem : cartItems)
			carts.add(Cart.builder().memberId(memberId).cartItem(cartItem).build());
		return add(carts);
	}

	@Override
	public long deleteCartItem(long memberId, long productId) {
		return delete(memberId, List.of(productId));
	}

	@Override
	public long deleteCartItems(long memberId, List<Long> productIds) {
		return delete(memberId, productIds);
	}

	@Override
	public List<GetCartItemResponse> getCartRequest(GetAuthenticatedCartRequest getAuthenticatedCartRequest) {
		String username = getAuthenticatedCartRequest.getUsername();
		long memberId = memberRepository.findByUsername(username).orElseThrow().getId();
		List<Cart> carts = cartRepository.findByMemberId(memberId);
		List<CartItem> cartItems = ListUtil.extractPropertyList(carts, Cart::getCartItem);
		return createCartItemResponses(cartItems);
	}

	@Override
	public List<GetCartItemResponse> getCartRequest(GetUnauthenticatedCartRequest getUnauthenticatedCartRequest) {
		String cookie = getUnauthenticatedCartRequest.getCookie();
		List<CartItem> cartItems = getCartItems(cookie);
		return createCartItemResponses(cartItems);
	}

	@Override
	public long addCartItemRequest(AddCartItemRequest addCartItemRequest) {
		String username = addCartItemRequest.getUsername();
		long memberId = memberRepository.findByUsername(username).orElseThrow().getId();
		Cart cart = addCartItemRequest.toEntity(memberId);
		return add(List.of(cart));
	}

	@Override
	public long deleteCartItemsRequest(DeleteCartItemsRequest deleteCartItemsRequest) {
		String username = deleteCartItemsRequest.getUsername();
		long memberId = memberRepository.findByUsername(username).orElseThrow().getId();
		List<Long> productIds = deleteCartItemsRequest.getProductIds();
		return delete(memberId, productIds);
	}

	private long add(List<Cart> carts) {
		long memberId = carts.get(0).getMemberId();
		List<Long> productIds = ListUtil.extractPropertyList(carts, Cart::getProductId);
		productIds.sort(null);
		List<Cart> previousList = cartRepository.findByMemberIdAndCartItemProductIdIn(memberId, productIds);
		if (!previousList.isEmpty()) {
			Iterator<Cart> prevIter = previousList.iterator();
			Cart prev = prevIter.next();
			for (Cart curr : carts) {
				if(Long.compare(curr.getProductId(), prev.getProductId())!= 0)
					continue;
				curr.modifyId(prev.getId());
				if(!prevIter.hasNext())
					break;
				prev = prevIter.next();
			}
		}
		return cartRepository.saveAll(carts).size();
	}

	private long delete(long memberId, List<Long> productIds) {
		return cartRepository.deleteAllByMemberIdAndCartItemProductIdIn(memberId, productIds);
	}

	private List<CartItem> getCartItems(String cookie) {
		List<CartItem> cartItems = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		String[] items;
		try {
			items = mapper.readValue(cookie, String[].class);
			for (String item : items) {
				String[] itemProperty = item.split(",");
				long productId = Long.parseLong(itemProperty[0], 16);
				int count = Integer.parseInt(itemProperty[1], 16);
				long timestamp = Long.parseLong(itemProperty[2], 16);
				cartItems.add(CartItem.builder().productId(productId).timestamp(timestamp).count(count).build());
			}
		} catch (JsonProcessingException e) {
			// TODO. 런타임 exception 변경
		}
		return cartItems;
	}

	private List<GetCartItemResponse> createCartItemResponses(List<CartItem> cartItems) {
		List<GetCartItemResponse> response = new ArrayList<>();
		List<Long> productIds = ListUtil.extractPropertyList(cartItems, CartItem::getProductId);
		List<Product> products = productRepository.findByIdIn(productIds);
		for (int i = 0; i < cartItems.size(); i++) {
			CartItem cartItem = cartItems.get(i);
			Product product = products.get(i);
			response.add(GetCartItemResponse.of(product, cartItem));
		}
		return response;
	}
}