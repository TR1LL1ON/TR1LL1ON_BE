package com.ybe.tr1ll1on.domain.cart.service;

import com.ybe.tr1ll1on.domain.cart.dto.CartItemDTO;
import com.ybe.tr1ll1on.domain.cart.model.CartItem;
import com.ybe.tr1ll1on.domain.cart.repository.CartItemRepository;
import com.ybe.tr1ll1on.domain.cart.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Comment("장바구니상품 전체조회")
    public List<CartItemDTO> getAllCartItems() {
        List<CartItem> cartItems = cartItemRepository.findAll();
        List<CartItemDTO> result = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            result.add(convertToCartItemDTO(cartItem));
        }
        return result;
    }

    @Comment("장바구니에 상품 추가")
    public void addProductToCart(){

    }

    @Comment("장바구니에 상품 삭제")
    public void removeFromCart(Long cartItemId){
        cartItemRepository.deleteById(cartItemId);
    }



    private CartItemDTO convertToCartItemDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setStartDate(cartItem.getStartDate());
        cartItemDTO.setEndDate(cartItem.getEndDate());
        cartItemDTO.setPersonNumber(cartItem.getPersonNumber());
        cartItemDTO.setPrice(cartItem.getPrice());
        cartItemDTO.setProductId(cartItem.getProduct().getId());
        cartItemDTO.setAccommodationId(cartItem.getAccommodation().getId());
        return cartItemDTO;
    }
}
