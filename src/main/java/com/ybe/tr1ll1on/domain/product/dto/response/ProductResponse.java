package com.ybe.tr1ll1on.domain.product.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    @Schema(description = "객실 id", defaultValue = "1")
    private Long roomId;

    @Schema(description = "객실 이름", defaultValue = "객실 이름")
    private String roomName;

    @Schema(description = "체크인 날짜", defaultValue = "2023-11-25")
    private String checkIn;

    @Schema(description = "체크아웃 날짜", defaultValue = "2023-11-26")
    private String checkOut;

    @Schema(description = "남은 객실 수", defaultValue = "1")
    private Integer count;

    @Schema(description = "1박당 평균 금액", defaultValue = "10000")
    private Double averPrice;

    @Schema(description = "전체 금액", defaultValue = "10000")
    private Integer totalPrice;

    @Schema(description = "객실 최대 인원수", defaultValue = "3")
    private Integer maxNumber;

    @Schema(description = "객실 기준 인원수", defaultValue = "2")
    private Integer standardNumber;

    @Schema(description = "객실 판매 가능 여부", defaultValue = "true")
    private boolean isSold;

    @Schema(description = "숙소 편의시설", defaultValue = "숙소 편의시설")
    private ProductFacilityResponse facility;

    @Schema(description = "숙소 이미지 주소 리스트", defaultValue = "숙소 이미지 주소 리스트")
    private List<ProductImageResponse> image;
}
