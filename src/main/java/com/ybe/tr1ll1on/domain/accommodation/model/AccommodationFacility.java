package com.ybe.tr1ll1on.domain.accommodation.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "accommodation_facility")
@Getter
@Builder
public class AccommodationFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facility")
    Long id;

    boolean hasCooking;
    boolean hasParking;
    boolean hasSports;
    boolean hasSauna;
    boolean hasBeauty;

    @OneToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }
}
