package com.ybe.tr1ll1on.domain.accommodation.model;

import com.ybe.tr1ll1on.domain.likes.model.Likes;
import com.ybe.tr1ll1on.domain.product.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accommodation")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accommodation_id")
    private Long id;

    private String name;

    private String address;

    private String latitude;

    private String longitude;

    private String areaCode;

    private String phone;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<AccommodationImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Product> productList = new ArrayList<>();

    @OneToOne(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private AccommodationFacility facility;

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Likes> likesList = new ArrayList<>();

    public void setCategory(Category category) {
        this.category = category;
    }

}
