package com.ybe.tr1ll1on.domain.order.response;

import com.ybe.tr1ll1on.domain.order.model.Orders;
import com.ybe.tr1ll1on.global.common.Payment;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

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
