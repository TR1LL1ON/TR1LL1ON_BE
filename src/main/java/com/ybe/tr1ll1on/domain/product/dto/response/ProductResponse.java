package com.ybe.tr1ll1on.domain.product.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    @Schema(example = "1")
    private Long roomId;

    @Schema(example = "디럭스 룸")
    private String roomName;

    @Schema(example = "2023-11-25")
    private String checkIn;

    @Schema(example = "2023-11-26")
    private String checkOut;

    @Schema(example = "1")
    private Integer count;

    @Schema(example = "10000")
    private Double averPrice;

    @Schema(example = "10000")
    private Integer totalPrice;

    @Schema(example = "3")
    private Integer maxNumber;

    @Schema(example = "2")
    private Integer standardNumber;

    @Schema(example = "true")
    private boolean isSold;

    @Schema(example = "숙소 편의시설")
    private ProductFacilityResponse facility;

    @ArraySchema(schema = @Schema(implementation = ProductImageResponse.class))
    private List<ProductImageResponse> image;
}
