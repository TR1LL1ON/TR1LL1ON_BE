package com.ybe.tr1ll1on.domain.cart.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer personNumber;
    private Integer price;

    @ManyToOne
    private Cart cart;

}
