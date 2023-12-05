package com.ybe.tr1ll1on.domain.product.model;

import com.ybe.tr1ll1on.domain.accommodation.model.Accommodation;
import com.ybe.tr1ll1on.domain.review.model.Review;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

//    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
//    private ProductFacility productFacility;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_facility_id")
    private ProductFacility productFacility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    @BatchSize(size = 100)
    private List<ProductImage> productImageList = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductInfoPerNight> productInfoPerNightList = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    @BatchSize(size = 100)
    private List<Review> reviewList = new ArrayList<>();

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public void setProductFacility(ProductFacility productFacility) {
        this.productFacility = productFacility;
    }
}
