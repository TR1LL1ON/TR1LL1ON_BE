package com.ybe.tr1ll1on.domain.order.dto.response;

import com.ybe.tr1ll1on.domain.order.model.OrderItem;
import com.ybe.tr1ll1on.global.common.ReviewStatus;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemResponse {
    @Schema(example = "3")
    private Long orderItemId;

    @Schema(example = "2023-12-01")
    private LocalDate checkIn;
    @Schema(example = "2023-12-02")
    private LocalDate checkOut;
    @Schema(example = "2")
    private Integer personNumber;
    @Schema(example = "50000")
    private Integer price;

    @ArraySchema(schema = @Schema(implementation = OrderItemDetailResponse.class))
    private OrderItemDetailResponse orderItemDetail;

    @Schema(example = "NO_WRITTEN")
    private ReviewStatus reviewStatus;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class OrderItemDetailResponse {
        @Schema(example = "자연닮은 치유농장")
        private String accommodationName;
        @Schema(example = "대구광역시 군위군 삼국유사면 화산산성길 65-1")
        private String accommodationAddress;
        @Schema(example = "http://tong.visitkorea.or.kr/cms/resource/37/3048137_image2_1.jpg")
        private String productImage;
        @Schema(example = "하늘채")
        private String productName;

        public static OrderItemDetailResponse fromEntity(OrderItem orderItem) {
            return OrderItemDetailResponse.builder()
                    .accommodationName(orderItem.getProduct().getAccommodation().getName())
                    .accommodationAddress(orderItem.getProduct().getAccommodation().getAddress())
                    .productImage(orderItem.getProduct().getProductImageList().get(0).getImageUrl())
                    .productName(orderItem.getProduct().getName())
                    .build();
        }
    }

    public static OrderItemResponse fromEntity(OrderItem orderItem) {
        return OrderItemResponse.builder()
                .orderItemId(orderItem.getId())
                .checkIn(orderItem.getStartDate())
                .checkOut(orderItem.getEndDate())
                .personNumber(orderItem.getPersonNumber())
                .price(orderItem.getPrice())
                .orderItemDetail(OrderItemDetailResponse.fromEntity(orderItem))
                .reviewStatus(orderItem.getReviewStatus())
                .build();
    }
}
