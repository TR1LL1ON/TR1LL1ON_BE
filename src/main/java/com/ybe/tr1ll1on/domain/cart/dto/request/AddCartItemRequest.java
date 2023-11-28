package com.ybe.tr1ll1on.domain.cart.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AddCartItemRequest {
    private Long productId;
    private Integer personNumber;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer price;

}
