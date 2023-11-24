package com.ybe.tr1ll1on.domain.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CartItemDto {
    private Long cartItemId;
    private Long productId;
    private Integer personNumber;
    private Integer price;
}
