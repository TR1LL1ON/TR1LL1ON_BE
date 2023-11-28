package com.ybe.tr1ll1on.domain.order.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ybe.tr1ll1on.domain.order.model.OrderItem;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderItemResponse {
    private Long orderItemId; // 주문

    private LocalDate checkIn; // 체크인
    private LocalDate checkOut; // 체크아웃
    private Integer personNumber; // 인원
    private Integer price; // 가격

    private OrderItemDetailResponse orderItemDetailResponse;

    private Boolean reviewWritten; // 리뷰 작성 여부

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class OrderItemDetailResponse {
        private String accommodationName; // 숙소 이름
        private String accommodationAddress; // 숙소 주소
        private String productImage; // 객실 이미지
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

                .checkIn(orderItem.getStartDate())
                .checkOut(orderItem.getEndDate())
                .personNumber(orderItem.getPersonNumber())
                .price(orderItem.getPrice())

                .orderItemDetailResponse(OrderItemDetailResponse.fromEntity(orderItem))

                .reviewWritten(orderItem.getReviewWritten())
                .build();
    }
}
