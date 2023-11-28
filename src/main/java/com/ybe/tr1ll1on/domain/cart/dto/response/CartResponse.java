package com.ybe.tr1ll1on.domain.cart.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class CartResponse {
    private Long cartItemId;
    private Long accommodationId;
    private Long productId;
    private String accommodationName;
    private String accommodationAddress;
    private String accommodationCategory;
    private String imageUrl;
    private String productName;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer personNumber;
    private Integer price;
}
