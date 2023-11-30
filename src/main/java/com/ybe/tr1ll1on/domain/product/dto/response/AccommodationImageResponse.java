package com.ybe.tr1ll1on.domain.product.dto.response;

import com.ybe.tr1ll1on.domain.accommodation.model.AccommodationImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccommodationImageResponse {

    @Schema(example = "http://tong.visitkorea.or.kr/cms/resource/50/2705650_image2_1.jpg")
    private String imageUrl;

    public static AccommodationImageResponse of(AccommodationImage ai) {
        return AccommodationImageResponse.builder()
                .imageUrl(ai.getImageUrl())
                .build();
    }
}
