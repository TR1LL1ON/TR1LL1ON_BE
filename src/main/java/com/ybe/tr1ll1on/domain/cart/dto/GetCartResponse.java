package com.ybe.tr1ll1on.domain.cart.dto;

import lombok.Data;

import java.util.List;

@Data
public class GetCartResponse {
    private Long cartId;
    private List<CartItemDto> cartItems;
}
