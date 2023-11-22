package com.ybe.tr1ll1on.domain.product.model;

import com.ybe.tr1ll1on.domain.accommodation.model.Accommodation;
import com.ybe.tr1ll1on.domain.cart.model.CartItem;
import com.ybe.tr1ll1on.domain.order.model.OrderItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Product {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer count;
    private String checkIn;
    private String checkOut;
    private Integer standardNumber;
    private Integer maxNumber;
    private String bedType;
    private Integer bedNumber;
    private Boolean isSmoke;
    private Boolean isBalcony;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "product")
    private OrderItem orderItem;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "product")
    private CartItem cartItem;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductInfo> productInfoList = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductImage> productImageList = new ArrayList<>();
}
