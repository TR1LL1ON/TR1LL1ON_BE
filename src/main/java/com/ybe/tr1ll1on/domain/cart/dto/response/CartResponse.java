package com.ybe.tr1ll1on.domain.cart.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CartResponse {
    @Schema(type = "Long", example = "20")
    private Long cartItemId;

    @Schema(type = "Long", example = "18")
    private Long accommodationId;

    @Schema(type = "Long", example = "55")
    private Long productId;

    @Schema(type = "String", example = "해와달수상낚시빌리지")
    private String accommodationName;

    @Schema(type = "String", example = "경기도 포천시 관인면 냉정리")
    private String accommodationAddress;

    @Schema(type = "String", example = "B02010700")
    private String accommodationCategory;

    @Schema(type = "String", example = "http://tong.visitkorea.or.kr/cms/resource/50/2705650_image2_1.jpg")
    private String imageUrl;

    @Schema(type = "String", example = "스탠다드")
    private String productName;

    @Schema(type = "LocalDate", example = "2023-12-25")
    private LocalDate checkIn;

    @Schema(type = "LocalDate", example = "2023-12-25")
    private LocalDate checkOut;

    @Schema(type = "Integer", example = "2")
    private Integer personNumber;

    @Schema(type = "Integer", example = "20000")
    private Integer price;
}
