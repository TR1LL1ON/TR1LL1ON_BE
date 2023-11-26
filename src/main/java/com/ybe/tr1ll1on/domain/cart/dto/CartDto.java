package com.ybe.tr1ll1on.domain.cart.dto;

import com.ybe.tr1ll1on.domain.cart.model.Cart;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class CartDto {
    private Long cartId;
    private List<CartItemDto> cartItems;

    public static CartDto from(Cart cart) {
        List<CartItemDto> cartItemDtos = cart.getCartItem().stream()
                .map(CartItemDto::from)
                .collect(Collectors.toList());

        return CartDto.builder()
                .cartId(cart.getId())
                .cartItems(cartItemDtos)
                .build();
    }
}