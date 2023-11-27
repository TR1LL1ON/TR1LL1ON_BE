package com.ybe.tr1ll1on.domain.cart.dto;

import com.ybe.tr1ll1on.domain.cart.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CartItemDto {
    private Long cartItemId;
    private Long productId;
    private Integer personNumber;
    private Integer price;
    private String productName;
    private String checkInTime;
    private String checkOutTime;

    public static CartItemDto from(CartItem cartItem) {
        return CartItemDto.builder()
                .cartItemId(cartItem.getId())
                .productId(cartItem.getProduct().getId())
                .personNumber(cartItem.getPersonNumber())
                .price(cartItem.getPrice())
                .productName(cartItem.getProduct().getName())
                .checkInTime(cartItem.getProduct().getCheckInTime())
                .checkOutTime(cartItem.getProduct().getCheckOutTime())
                .build();
    }
}