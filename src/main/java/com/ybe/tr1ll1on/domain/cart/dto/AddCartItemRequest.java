package com.ybe.tr1ll1on.domain.cart.dto;

import lombok.Data;

@Data
public class AddCartItemRequest {
    private Long userId;
    private Long productId;
    private Integer personNumber;
}
