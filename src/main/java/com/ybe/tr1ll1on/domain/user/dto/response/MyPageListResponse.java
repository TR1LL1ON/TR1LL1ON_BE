package com.ybe.tr1ll1on.domain.user.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ybe.tr1ll1on.domain.order.model.Orders;
import com.ybe.tr1ll1on.global.common.Payment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPageListResponse {
    private Long orderId;
    private LocalDateTime orderCreateDate;
    private Payment payment;
    private Integer totalPrice;

    public static MyPageListResponse fromEntity(Orders order) {
        return MyPageListResponse.builder()
                .orderId(order.getId())
                .orderCreateDate(order.getOrderCreateDate())
                .payment(order.getPayment())
                .totalPrice(order.getTotalPrice())
                .build();
    }
}