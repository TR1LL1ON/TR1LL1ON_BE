package com.ybe.tr1ll1on.domain.accommodation.model;

import com.ybe.tr1ll1on.domain.likes.model.Likes;
import com.ybe.tr1ll1on.domain.product.model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accommodation_id")
    private Long id;
    private String name;
    private String info;
    private String address;
    private String latitude;
    private String longitude;
    private String regionCode;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private AccommodationCategory category;

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<AccommodationImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Product> productList = new ArrayList<>();

    @OneToOne(mappedBy = "accommodation", cascade = CascadeType.ALL)
    private AccommodationFacility facility;

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Likes> likesList = new ArrayList<>();
}
