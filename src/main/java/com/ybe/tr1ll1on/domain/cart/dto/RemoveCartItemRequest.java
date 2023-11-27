package com.ybe.tr1ll1on.domain.cart.dto;

import lombok.Data;

@Data
public class RemoveCartItemRequest {
    private Long userId;
    private Long cartItemId;
}
