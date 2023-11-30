package com.ybe.tr1ll1on.domain.cart.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddCartItemResponse {

    @Schema(example = "20")
    private Long cartItemId;

    @Schema(example = "55")
    private Long productId;

    @Schema(example = "2")
    private Integer personNumber;
}
