package com.ybe.tr1ll1on.domain.order.dto;

import com.ybe.tr1ll1on.domain.order.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponse {
    private Long orderItemId;

    private LocalDate startDate;
    private LocalDate endDate;
    private Integer personNumber;
    private Integer price;

    private boolean reviewWritten; // 리뷰 작성 여부

    public static OrderItemResponse fromEntity(OrderItem orderItem) {
        return OrderItemResponse.builder()
                .orderItemId(orderItem.getId())
                .startDate(orderItem.getStartDate())
                .endDate(orderItem.getEndDate())
                .personNumber(orderItem.getPersonNumber())
                .price(orderItem.getPrice())
                .reviewWritten(orderItem.getReviewWritten())
                .build();
    }
}