package com.ybe.tr1ll1on.domain.product.response;

import com.ybe.tr1ll1on.domain.accommodation.model.AccommodationImage;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccommodationImageResponse {
    private String imageUrl;

    public static AccommodationImageResponse of(AccommodationImage ai) {
        return AccommodationImageResponse.builder()
                .imageUrl(ai.getImageUrl())
                .build();
    }
}
