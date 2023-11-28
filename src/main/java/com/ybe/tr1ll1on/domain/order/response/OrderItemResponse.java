package com.ybe.tr1ll1on.domain.order.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ybe.tr1ll1on.domain.order.model.OrderItem;
import com.ybe.tr1ll1on.domain.order.request.OrderItemRequest;
import jakarta.validation.constraints.NotNull;
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
public class OrderItemResponse {
    private LocalDate checkIn;

    private LocalDate checkOut;

    private Integer personNumber;

    private Integer price;

    private Long productId;

    private Boolean reviewWritten; // 리뷰 작성 여부

    public static OrderItemResponse of(OrderItem oi) {
        return OrderItemResponse.builder()
                .checkIn(oi.getStartDate())
                .checkOut(oi.getEndDate())
                .personNumber(oi.getPersonNumber())
                .price(oi.getPrice())
                .productId(oi.getProduct().getId())
                .reviewWritten(oi.getReviewWritten())
                .build();
    }
}
