package com.ybe.tr1ll1on.domain.product.dto.response;

import com.ybe.tr1ll1on.domain.review.dto.response.ProductReviewListResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationDetailResponse {

    @Schema(example = "1")
    private long accommodationId;

    @Schema(example = "플로팅웨일 설악도적폭포스테이")
    private String name;

    @Schema(example = "강원특별자치도 인제군 북면 미시령옛길 140")
    private String address;

    @Schema(example = "1")
    private String areaCode;

    @Schema(example = "000-0000-0000")
    private String phone;

    @Schema(example = "B02010100")
    private String category;

    @Schema(example = "2023-11-25")
    private LocalDate checkIn;

    @Schema(example = "2023-11-26")
    private LocalDate checkOut;

    @Schema(example = "2")
    private int personNumber;

    @Schema(example = "5.0")
    private double score;

    @ArraySchema(schema = @Schema(implementation = AccommodationImageResponse.class))
    private List<AccommodationImageResponse> image;

    @ArraySchema(schema = @Schema(implementation = ProductResponse.class))
    private List<ProductResponse> rooms;

    @Schema(example = "숙소 편의시설")
    private AccommodationFacilityResponse facility;

    @ArraySchema(schema = @Schema(implementation = ProductReviewListResponse.class))
    private List<ProductReviewListResponse> reviews;

    @Schema(example = "37.65908725483671")
    private String latitude;

    @Schema(example = "127.00565519177638")
    private String longitude;

}
