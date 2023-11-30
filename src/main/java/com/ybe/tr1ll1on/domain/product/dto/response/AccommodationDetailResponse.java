package com.ybe.tr1ll1on.domain.product.dto.response;

import java.time.LocalDate;
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
public class AccommodationDetailResponse {

    @Schema(description = "숙소 id", defaultValue = "1")
    private long accommodationId;

    @Schema(description = "숙소 이름", defaultValue = "숙소 이름")
    private String name;

    @Schema(description = "숙소 주소", defaultValue = "숙소 주소")
    private String address;

    @Schema(description = "지역코드", defaultValue = "1")
    private String areaCode;

    @Schema(description = "숙소 전화번호", defaultValue = "000-0000-0000")
    private String phone;

    @Schema(description = "숙소 카테고리", defaultValue = "B02010100")
    private String category;

    @Schema(description = "체크인 날짜", defaultValue = "2023-11-25")
    private LocalDate checkIn;

    @Schema(description = "체크아웃 날짜", defaultValue = "2023-11-26")
    private LocalDate checkOut;

    @Schema(description = "인원수", defaultValue = "2")
    private int personNumber;

    @Schema(description = "평점", defaultValue = "5.0")
    private double score;

    @Schema(description = "숙소 이미지 주소 리스트", defaultValue = "숙소 이미지 주소 리스트")
    private List<AccommodationImageResponse> image;

    @Schema(description = "숙소 객실 리스트", defaultValue = "숙소 객실 리스트")
    private List<ProductResponse> rooms;

    @Schema(description = "숙소 편의시설", defaultValue = "숙소 편의시설")
    private AccommodationFacilityResponse facility;

    @Schema(description = "숙소 위치 위도", defaultValue = "숙소 위치 위도")
    private String latitude;

    @Schema(description = "숙소 위치 경도", defaultValue = "숙소 위치 경도")
    private String longitude;

}
