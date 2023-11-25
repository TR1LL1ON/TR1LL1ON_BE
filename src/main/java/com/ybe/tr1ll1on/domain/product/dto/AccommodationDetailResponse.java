package com.ybe.tr1ll1on.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ybe.tr1ll1on.global.dto.ImageResponse;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccommodationDetailResponse {
    private long accommodationId;

    private String name;

    private String address;

    private String areaCode;

    private String phone;

    private String category;

    private LocalDate checkIn;

    private LocalDate checkOut;

    private int personNumber;

    private double score;

    private List<ImageResponse> image;

    private List<ProductResponse> rooms;

    private boolean hasBeauty;

    private boolean hasCooking;

    private boolean hasSauna;

    private boolean hasParking;

    private boolean hasSports;

    private String latitude;

    private String longitude;

}
