package com.ybe.tr1ll1on.domain.accommodation.model;

import com.ybe.tr1ll1on.domain.likes.model.Likes;
import com.ybe.tr1ll1on.domain.product.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accommodation")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

//    @OneToOne(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private AccommodationFacility facility;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_facility_id")
    private AccommodationFacility facility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.REMOVE)
    @BatchSize(size = 100)
    private List<AccommodationImage> accommodationImageList = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Likes> likesList = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> productList = new ArrayList<>();

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setId(Long accommodationId) {
        this.id = accommodationId;
    }

    public void setFacility(AccommodationFacility facility) {
        this.facility = facility;
    }
}
