package com.ybe.tr1ll1on.domain.user.dto.response;

import com.ybe.tr1ll1on.domain.order.model.OrderItem;
import com.ybe.tr1ll1on.domain.order.model.Orders;
import com.ybe.tr1ll1on.global.common.Payment;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPageResponse {
    @Schema(example = "1")
    private Long orderId;

    @Schema(example = "2023-11-28T11:17:38.000+00:00")
    private LocalDateTime orderCreateDate;
    @Schema(example = "KAKAOPAY")
    private Payment payment;
    @Schema(example = "100000")
    private Integer totalPrice;

    @ArraySchema(schema = @Schema(implementation = MyPageResponse.AccommodationDetails.class))
    private AccommodationDetails accommodation;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AccommodationDetails {
        @Schema(example = "http://tong.visitkorea.or.kr/cms/resource/37/3048137_image2_1.jpg")
        private List<String> accommodationImages;
        @Schema(example = "자연닮은 치유농장")
        private List<String> accommodationNames;
        @Schema(example = "하늘채")
        private List<String> productNames;

        public static AccommodationDetails fromEntity(List<OrderItem> orderItems) {

            return AccommodationDetails.builder()
                    .accommodationImages(orderItems.stream()
                            .map(orderItem -> orderItem.getProduct().getAccommodation().getAccommodationImageList().get(0).getImageUrl())
                            .distinct()
                            .collect(Collectors.toList()))
                    .accommodationNames(orderItems.stream()
                            .map(orderItem -> orderItem.getProduct().getAccommodation().getName())
                            .distinct()
                            .collect(Collectors.toList()))
                    .productNames(orderItems.stream()
                            .map(orderItem -> orderItem.getProduct().getName())
                            .distinct()
                            .collect(Collectors.toList()))
                    .build();
        }
    }

    public static MyPageResponse fromEntity(Orders order) {
        List<OrderItem> orderItems = order.getOrderItemList();

        return MyPageResponse.builder()
                .orderId(order.getId())
                .orderCreateDate(order.getOrderCreateDate())
                .payment(order.getPayment())
                .totalPrice(order.getTotalPrice())
                .accommodation(AccommodationDetails.fromEntity(orderItems))
                .build();
    }
}