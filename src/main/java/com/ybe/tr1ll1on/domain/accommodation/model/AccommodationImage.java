package com.ybe.tr1ll1on.domain.accommodation.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "accommodation_image")
@Getter
@Builder
public class AccommodationImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accommodation_image_id")
    private Long id;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }
}
