package com.ybe.tr1ll1on.domain.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Long cartItemId;
    private String startDate;
    private String endDate;
    private Integer personNumber;
    private Integer price;
}
