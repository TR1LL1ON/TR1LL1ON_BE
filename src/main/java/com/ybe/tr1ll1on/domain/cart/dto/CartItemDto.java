package com.ybe.tr1ll1on.domain.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemDto {
    private Long cartItemId;
    private Long productId;
    private Integer personNumber;
    private Integer price;
    private String productName;
    private String checkInTime;
    private String checkOutTime;
}