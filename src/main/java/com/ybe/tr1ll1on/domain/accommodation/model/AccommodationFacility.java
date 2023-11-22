package com.ybe.tr1ll1on.domain.accommodation.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class AccommodationFacility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accommodation_facility_id")
    private Long id;
    private Boolean swimmingPool;
    private Boolean breakfast;
    private Boolean party;
    private Boolean parking;
    private Boolean cooking;

    @OneToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;
}
