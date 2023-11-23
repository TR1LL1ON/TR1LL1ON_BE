package com.ybe.tr1ll1on.domain.accommodation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accommodation_facility")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationFacility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accommodation_facility_id")
    private Long id;
    private Boolean hasCooking;
    private Boolean hasParking;
    private Boolean hasSports;
    private Boolean hasSauna;
    private Boolean hasBeauty;

    @OneToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }
}
