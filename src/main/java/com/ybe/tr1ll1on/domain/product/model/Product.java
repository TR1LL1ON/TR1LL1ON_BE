package com.ybe.tr1ll1on.domain.product.model;

import com.ybe.tr1ll1on.domain.accommodation.model.Accommodation;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "product")
@NoArgsConstructor
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;
    private String checkInTime;
    private String checkOutTime;
    private int standardNumber;
    private int maximumNumber;
    private int count;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private ProductFacility facility;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> images;

    @Builder
    public Product(String name, String checkInTime, String checkOutTime, int standardNumber, int maximumNumber, int count) {
        this.name = name;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.standardNumber = standardNumber;
        this.maximumNumber = maximumNumber;
        this.count = count;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }
}
