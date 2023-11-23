package com.ybe.tr1ll1on.domain.product.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ProductFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_facility_id")
    private Long id;

    private boolean hasBath;
    private boolean hasAirCondition;
    private boolean hasTV;
    private boolean hasPC;
    private boolean hasCable;
    private boolean hasInternet;
    private boolean hasRefrigerator;
    private boolean hasToiletries;
    private boolean hasSofa;
    private boolean canCook;
    private boolean hasTable;
    private boolean hasHairDryer;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
