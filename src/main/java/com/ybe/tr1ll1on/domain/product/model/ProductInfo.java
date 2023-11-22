package com.ybe.tr1ll1on.domain.product.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class ProductInfo {
    @Id
    @Column(name = "product_info_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private Integer price;
    private Integer count;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
