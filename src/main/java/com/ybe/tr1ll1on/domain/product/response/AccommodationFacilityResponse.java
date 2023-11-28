package com.ybe.tr1ll1on.domain.product.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ybe.tr1ll1on.domain.accommodation.model.AccommodationFacility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationFacilityResponse {
    private boolean hasBeauty;

    private boolean hasCooking;

    private boolean hasSauna;

    private boolean hasParking;

    private boolean hasSports;

    public static AccommodationFacilityResponse of(AccommodationFacility facility) {
        return AccommodationFacilityResponse.builder()
                .hasCooking(facility.getHasCooking())
                .hasBeauty(facility.getHasBeauty())
                .hasParking(facility.getHasParking())
                .hasSauna(facility.getHasSauna())
                .hasSports(facility.getHasSports())
                .build();
    }
}
