package com.ybe.tr1ll1on.domain.accommodation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccommodationMapResponse {

    @Schema(example = "1")
    private Long accommodationId;

    @Schema(example = "플로팅웨일 설악도적폭포스테이")
    private String name;

    @Schema(example = "37.65908725483671")
    private String latitude;

    @Schema(example = "127.00565519177638")
    private String longitude;
}
