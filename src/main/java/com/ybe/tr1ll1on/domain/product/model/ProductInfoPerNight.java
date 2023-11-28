package com.ybe.tr1ll1on.domain.product.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProductInfoPerNight {
    @Id
    @Column(name = "product_info_per_night_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private Integer price;
    private Integer count;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public void decreaseCountByOne() {
        this.count--;
    }
}
