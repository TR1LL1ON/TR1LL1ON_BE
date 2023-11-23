package com.ybe.tr1ll1on.domain.accommodation.model;


import com.ybe.tr1ll1on.domain.product.model.Product;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Entity
@Table(name = "accommodation")
@Getter
@Builder
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accommodation_id")
    private Long id;

    private String name;
    private String address;
    private String phone;
    private double longitude;
    private double latitude;

    private String areaCode;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.REMOVE)
    private List<AccommodationImage> images;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(mappedBy = "accommodation")
    private AccommodationFacility accommodationFacility;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL)
    private List<Product> products;

    public void setCategory(Category category) {
        this.category = category;
    }
}
