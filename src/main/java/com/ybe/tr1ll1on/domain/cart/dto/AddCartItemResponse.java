package com.ybe.tr1ll1on.domain.cart.dto;

import lombok.Data;

@Data
public class AddCartItemResponse {
    private Long cartItemId;
    private Long productId;
    private Integer personNumber;
}
