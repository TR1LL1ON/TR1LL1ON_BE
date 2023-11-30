package com.ybe.tr1ll1on.domain.user.dto.response;

import com.ybe.tr1ll1on.domain.order.model.OrderItem;
import com.ybe.tr1ll1on.domain.order.model.Orders;
import com.ybe.tr1ll1on.global.common.Payment;
import java.time.LocalDateTime;
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
    private Long orderId;
    private LocalDateTime orderCreateDate;
    private Payment payment;
    private Integer totalPrice;

    private List<String> accommodationImages;
    private List<String> accommodationNames;
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