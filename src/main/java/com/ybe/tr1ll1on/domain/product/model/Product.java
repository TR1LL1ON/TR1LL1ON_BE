package com.ybe.tr1ll1on.domain.product.model;

import com.ybe.tr1ll1on.domain.accommodation.model.Accommodation;
import com.ybe.tr1ll1on.domain.cart.model.CartItem;
import com.ybe.tr1ll1on.domain.order.model.OrderItem;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@NoArgsConstructor
@Getter
public class Product {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer count;
    private String checkInTime;
    private String checkOutTime;
    private Integer standardNumber;
    private Integer maximumNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

//    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private OrderItem orderItem;
//
//    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private CartItem cartItem;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductInfoPerNight> productInfoPerNightList = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductImage> productImageList = new ArrayList<>();

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private ProductFacility productFacility;

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    @Builder
    public Product(String name, String checkInTime,
            String checkOutTime, int standardNumber, int maximumNumber, int count) {
        this.name = name;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.standardNumber = standardNumber;
        this.maximumNumber = maximumNumber;
        this.count = count;
    }

}
