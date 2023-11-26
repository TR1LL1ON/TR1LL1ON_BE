package com.ybe.tr1ll1on.domain.cart.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetCartRequest {
    private Long userId;
}
