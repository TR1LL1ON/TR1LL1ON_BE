package com.ybe.tr1ll1on.domain.order.dto.response;

import com.ybe.tr1ll1on.domain.order.model.Orders;
import com.ybe.tr1ll1on.global.common.Payment;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

    @ArraySchema(schema = @Schema(implementation = OrderItemResponse.class))
    private List<OrderItemResponse> orders;

    @Schema(example = "KAKAOPAY")
    private Payment payment;

    @Schema(example = "20000")
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
