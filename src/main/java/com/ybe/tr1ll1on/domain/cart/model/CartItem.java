package com.ybe.tr1ll1on.domain.cart.model;

import com.ybe.tr1ll1on.domain.product.model.Product;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class CartItem {
    @Id
    @Column(name = "cart_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer personNumber;
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;


    @Builder
    public CartItem(LocalDate startDate, LocalDate endDate, Integer personNumber, Integer price, Cart cart, Product product) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.personNumber = personNumber;
        this.price = price;
        this.cart = cart;
        this.product = product;
    }
}
