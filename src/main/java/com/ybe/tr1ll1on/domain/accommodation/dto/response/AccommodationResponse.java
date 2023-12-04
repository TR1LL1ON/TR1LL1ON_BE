package com.ybe.tr1ll1on.domain.accommodation.dto.response;

import com.ybe.tr1ll1on.domain.accommodation.model.AccommodationImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccommodationResponse {

    @Schema(example = "1")
    private Long accommodationId;

    @Schema(example = "http://tong.visitkorea.or.kr/cms/resource/50/2705650_image2_1.jpg")
    private String imageUrl;

    @Schema(example = "플로팅웨일 설악도적폭포스테이")
    private String name;

    @Schema(example = "강원특별자치도 인제군 북면 미시령옛길 140")
    private String address;

    @Schema(example = "32")
    private String areaCode;

    @Schema(example = "50000")
    private Integer price;

    @Schema(example = "2.01")
    private Double score;

    @Schema(example = "37.65908725483671")
    private String latitude;

    @Schema(example = "127.00565519177638")
    private String longitude;

}
