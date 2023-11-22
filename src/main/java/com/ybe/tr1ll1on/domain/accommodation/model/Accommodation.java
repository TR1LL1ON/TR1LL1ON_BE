package com.ybe.tr1ll1on.domain.accommodation.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String addressCode;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "accommodation_category_id")
    private AccommodationCategory category;

    @OneToMany(mappedBy = "accommodation", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<AccommodationImage> images;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "accommodation")
    private AccommodationFacility facility;
}
