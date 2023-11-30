package com.ybe.tr1ll1on.domain.order.response;

import com.ybe.tr1ll1on.domain.order.model.OrderItem;
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
    private Long orderItemId; // 주문


    @Schema(example = "10")
    private Long productId; //상품 아이디

    @Schema(example = "2023-12-01")
    private LocalDate checkIn; // 체크인

    @Schema(example = "2023-12-02")
    private LocalDate checkOut; // 체크아웃

    @Schema(example = "2")
    private Integer personNumber; // 인원

    @Schema(example = "50000")
    private Integer price; // 가격


    @ArraySchema(schema = @Schema(implementation = OrderItemDetailResponse.class))
    private OrderItemDetailResponse orderItemDetail;


    @Schema(example = "false")
    private Boolean reviewWritten; // 리뷰 작성 여부

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class OrderItemDetailResponse {

        @Schema(example = "자연닮은 치유농장")
        private String accommodationName; // 숙소 이름

        @Schema(example = "대구광역시 군위군 삼국유사면 화산산성길 65-1")
        private String accommodationAddress; // 숙소 주소

        @Schema(example = "http://tong.visitkorea.or.kr/cms/resource/37/3048137_image2_1.jpg")
        private String productImage; // 객실 이미지

        @Schema(example = "하늘채")
        private String productName; // 객실 이름

        public static OrderItemDetailResponse fromEntity(OrderItem orderItem) {

            System.out.println(orderItem.getProduct().getId());

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
                .productId(orderItem.getProduct().getId())
                .checkIn(orderItem.getStartDate())
                .checkOut(orderItem.getEndDate())
                .personNumber(orderItem.getPersonNumber())
                .price(orderItem.getPrice())
                .orderItemDetail(OrderItemDetailResponse.fromEntity(orderItem))
                .reviewWritten(orderItem.getReviewWritten())
                .build();
    }
}
