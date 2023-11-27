package com.ybe.tr1ll1on.domain.accommodation.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccommodationResponseDTO {
    private Long accommodationId;
    private String imageUrl;
    private String name;
    private String address;
    private String areaCode;
    private Integer price;
    private Double score;
}
