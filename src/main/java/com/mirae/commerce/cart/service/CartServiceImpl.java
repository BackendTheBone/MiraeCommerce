package com.mirae.commerce.cart.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mirae.commerce.cart.dto.*;
import com.mirae.commerce.cart.entity.Cart;
import com.mirae.commerce.cart.entity.CartItem;
import com.mirae.commerce.cart.repository.CartRepository;
import com.mirae.commerce.common.utils.ListUtil;
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
	private final ProductRepository productRepository;

	@Override
	public long addCarts(String username, String cookie) {
		List<Cart> carts = new ArrayList<>();
		List<CartItem> cartItems = getCartItemList(cookie);
		for (CartItem cartItem : cartItems)
			carts.add(Cart.builder().username(username).cartItem(cartItem).build());
		return add(carts);
	}

	@Override
	public long deleteCart(String username, long productId) {
		return delete(username, List.of(productId));
	}

	@Override
	public long deleteCarts(String username, List<Long> productIds) {
		return delete(username, productIds);
	}

	@Override
	public List<GetCartResponse> getCartsRequest(GetAuthenticatedCartsRequest getAuthenticatedCartRequest) {
		String username = getAuthenticatedCartRequest.getUsername();
		List<Cart> carts = cartRepository.findAllByUsername(username);
		List<CartItem> cartItems = ListUtil.extractPropertyList(carts, Cart::getCartItem);
		return createCartResponseList(cartItems);
	}

	@Override
	public List<GetCartResponse> getCartsRequest(GetUnauthenticatedCartsRequest getUnauthenticatedCartRequest) {
		String cookie = getUnauthenticatedCartRequest.getCookie();
		List<CartItem> cartItems = getCartItemList(cookie);
		return createCartResponseList(cartItems);
	}

	@Override
	public long addCartRequest(AddCartRequest addCartRequest) {
		Cart cart = addCartRequest.toEntity();
		return add(List.of(cart));
	}

	@Override
	public long deleteCartsRequest(DeleteCartsRequest deleteCartsRequest) {
		String username = deleteCartsRequest.getUsername();
		List<Long> productIds = deleteCartsRequest.getProductIds();
		return delete(username, productIds);
	}

	private long add(List<Cart> carts) {
		String username = carts.get(0).getUsername();
		List<Long> productIds = ListUtil.extractPropertyList(carts, Cart::getProductId);
		productIds.sort(null);
		List<Cart> previousList = cartRepository.findAllByUsernameAndCartItemProductIdIn(username, productIds);
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

	private long delete(String username, List<Long> productIds) {
		return cartRepository.deleteAllByUsernameAndCartItemProductIdIn(username, productIds);
	}

	private List<CartItem> getCartItemList(String cookie) {
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

	private List<GetCartResponse> createCartResponseList(List<CartItem> cartItems) {
		List<GetCartResponse> response = new ArrayList<>();
		List<Long> productIds = ListUtil.extractPropertyList(cartItems, CartItem::getProductId);
		List<Product> products = productRepository.findAllByIdIn(productIds);
		for (int i = 0; i < cartItems.size(); i++) {
			CartItem cartItem = cartItems.get(i);
			Product product = products.get(i);
			response.add(GetCartResponse.of(product, cartItem));
		}
		return response;
	}
}