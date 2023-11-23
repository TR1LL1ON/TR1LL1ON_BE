package com.ybe.tr1ll1on.domain.cart.dto;

import com.ybe.tr1ll1on.domain.cart.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private List<CartItemResponse> items;

    public static CartResponse fromEntity(Cart cart) {
        List<CartItemResponse> itemResponses = cart.getCartItem().stream()
                .map(CartItemResponse::fromEntity)
                .collect(Collectors.toList());

        return new CartResponse(itemResponses);
    }
}
