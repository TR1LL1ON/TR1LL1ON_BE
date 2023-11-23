package com.ybe.tr1ll1on.domain.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer personNumber;
    private Integer price;
    private Long productId;
    private Long accommodationId;
}
