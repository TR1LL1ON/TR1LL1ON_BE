package com.ybe.tr1ll1on.domain.cart.service;

import com.ybe.tr1ll1on.domain.cart.dto.CartItemResponse;
import com.ybe.tr1ll1on.domain.cart.model.CartItem;
import com.ybe.tr1ll1on.domain.cart.repository.CartItemRepository;
import com.ybe.tr1ll1on.domain.cart.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Comment("장바구니상품 전체조회")
    public List<CartItemResponse> getAllCartItems() {
        List<CartItem> cartItems = cartItemRepository.findAll();
        return cartItems.stream()
                .map(CartItemResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Comment("장바구니에 상품 추가")
    public void addProductToCart(){

    }

    @Comment("장바구니에 상품 삭제")
    public void removeFromCart(Long cartItemId){
        cartItemRepository.deleteById(cartItemId);
    }



}
