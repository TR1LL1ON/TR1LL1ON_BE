package com.ybe.tr1ll1on.domain.accommodation.dto.response;

import com.ybe.tr1ll1on.domain.accommodation.model.AccommodationImage;
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

    public AccommodationResponseDTO(Long accommodationId, String imageUrl, String name, String address, String areaCode, Integer price) {
        this.accommodationId = accommodationId;
        this.imageUrl = imageUrl==null ? AccommodationImage.BASIC_ACCOMMODATION_IMG : imageUrl;
        this.name = name;
        this.address = address;
        this.areaCode = areaCode;
        this.price = price;
    }

    public AccommodationResponseDTO(Long accommodationId, String name) {
        this.accommodationId = accommodationId;
        this.name = name;
    }
}
