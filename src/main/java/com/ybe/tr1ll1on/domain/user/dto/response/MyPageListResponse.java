package com.ybe.tr1ll1on.domain.user.dto.response;

import com.ybe.tr1ll1on.domain.order.model.OrderItem;
import com.ybe.tr1ll1on.domain.order.model.Orders;
import com.ybe.tr1ll1on.global.common.Payment;
import java.time.LocalDateTime;

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
public class MyPageListResponse {
    @Schema(description = "주문 id", defaultValue = "1")
    private Long orderId;

    @Schema(description = "주문 생성 시각", defaultValue = "2023-11-28T11:17:38.000+00:00")
    private LocalDateTime orderCreateDate;

    @Schema(description = "결제 수단", defaultValue = "KAKAOPAY")
    private Payment payment;

    @Schema(description = "총 결제 금액", defaultValue = "100000")
    private Integer totalPrice;

    @Schema(example = "http://tong.visitkorea.or.kr/cms/resource/37/3048137_image2_1.jpg")
    private List<String> accommodationImages;

    @Schema(example = "자연닮은 치유농장")
    private List<String> accommodationNames;

    @Schema(example = "하늘채")
    private List<String> productNames;

    public static MyPageListResponse fromEntity(Orders order) {
        List<OrderItem> orderItemList = order.getOrderItemList();

        List<String> accommodationImages = orderItemList.stream()
                .map(orderItem -> orderItem.getProduct().getAccommodation().getImages().get(0).getImageUrl())
                .distinct()
                .collect(Collectors.toList());

        List<String> accommodationNames = orderItemList.stream()
                .map(orderItem -> orderItem.getProduct().getAccommodation().getName())
                .distinct()
                .collect(Collectors.toList());

        List<String> productNames = orderItemList.stream()
                .map(orderItem -> orderItem.getProduct().getName())
                .distinct()
                .collect(Collectors.toList());

        return MyPageListResponse.builder()
                .orderId(order.getId())
                .orderCreateDate(order.getOrderCreateDate())
                .payment(order.getPayment())
                .totalPrice(order.getTotalPrice())
                .accommodationImages(accommodationImages)
                .accommodationNames(accommodationNames)
                .productNames(productNames)
                .build();
    }
}