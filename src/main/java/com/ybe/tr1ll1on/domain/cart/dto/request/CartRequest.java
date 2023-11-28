package com.ybe.tr1ll1on.domain.cart.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CartRequest {
    private Long productId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer personNumber;
    private Integer price;
}
