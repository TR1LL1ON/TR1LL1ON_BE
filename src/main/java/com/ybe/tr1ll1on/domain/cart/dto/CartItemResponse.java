package com.ybe.tr1ll1on.domain.cart.dto;

import com.ybe.tr1ll1on.domain.cart.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
    private String accomodationName;
    private String accomodationAddress;
    private String accomodationCategory;
    private String productName;
    private String checkIn;
    private String checkOut;
    private Integer personNumber;
    private Integer price;

    public static CartItemResponse fromEntity(CartItem cartItem) {
        return new CartItemResponse(
                cartItem.getAccommodation().getName(),
                cartItem.getAccommodation().getAddress(),
                cartItem.getAccommodation().getCategory(),
                cartItem.getProduct().getName(),
                cartItem.getStartDate().toString(),
                cartItem.getEndDate().toString(),
                cartItem.getPersonNumber(),
                cartItem.getPrice()
        );
    }
}
