package com.ybe.tr1ll1on.domain.cart.dto;

import com.ybe.tr1ll1on.domain.cart.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private LocalDate checkInTime;
    private LocalDate checkOutTime;

    public static CartItemDto from(CartItem cartItem) {
        return CartItemDto.builder()
                .cartItemId(cartItem.getId())
                .productId(cartItem.getProduct().getId())
                .personNumber(cartItem.getPersonNumber())
                .price(cartItem.getPrice())
                .productName(cartItem.getProduct().getName())
                .checkInTime(LocalDate.parse(cartItem.getProduct().getCheckInTime()))
                .checkOutTime(LocalDate.parse(cartItem.getProduct().getCheckOutTime()))
                .build();
    }
}