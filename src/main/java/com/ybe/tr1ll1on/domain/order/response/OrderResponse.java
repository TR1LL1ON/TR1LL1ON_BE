package com.ybe.tr1ll1on.domain.order.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ybe.tr1ll1on.domain.order.model.Orders;
import com.ybe.tr1ll1on.global.common.Payment;
import java.util.List;
import java.util.stream.Collectors;
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
public class OrderResponse {
    private List<OrderItemResponse> orders;

    private Payment payment;

    private Integer totalPrice;



    public static OrderResponse of(Orders orders) {
        return OrderResponse.builder()
                .orders(
                    orders.getOrderItemList()
                       .stream()
                       .map(it -> OrderItemResponse.fromEntity(it))
                       .collect(Collectors.toList())
                )
                .payment(orders.getPayment())
                .totalPrice(orders.getTotalPrice())
                .build();
    }
}
