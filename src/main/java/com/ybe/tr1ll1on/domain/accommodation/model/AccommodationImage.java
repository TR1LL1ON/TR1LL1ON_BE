package com.ybe.tr1ll1on.domain.accommodation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accommodation_image")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationImage {

    public static final String BASIC_ACCOMMODATION_IMG ="https://file.notion.so/f/f/7832e073-41e7-42fa-bde1-be449c2ae653/d781a1ed-399a-4e7c-a685-1345854836e7/Untitled.png?id=3e1513b0-8771-4977-8f3f-312841ba94e3&table=block&spaceId=7832e073-41e7-42fa-bde1-be449c2ae653&expirationTimestamp=1701043200000&signature=G-Hl7Ke4BxCYKYmQUblHz6zObkeKuq4jDuAkTUUblyg&downloadName=Untitled.png";

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
