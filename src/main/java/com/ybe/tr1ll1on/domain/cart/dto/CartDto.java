package com.ybe.tr1ll1on.domain.cart.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDto {
    private Long cartId;
    private List<CartItemDto> cartItems;
}