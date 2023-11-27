package com.ybe.tr1ll1on.domain.product.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
public class ProductResponse {
    private Long roomId;

    private String roomName;

    private String checkIn;

    private String checkOut;

    private Integer count;

    private Double averPrice;

    private Integer totalPrice;

    private Integer maxNumber;

    private Integer standardNumber;

    private boolean isSold;

    private ProductFacilityResponse facility;

    private List<ProductImageResponse> image;
}
