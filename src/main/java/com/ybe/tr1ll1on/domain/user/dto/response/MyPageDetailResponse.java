package com.ybe.tr1ll1on.domain.user.dto.response;

import com.ybe.tr1ll1on.domain.order.model.Orders;
import com.ybe.tr1ll1on.domain.order.dto.response.OrderItemResponse;
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
public class MyPageDetailResponse {
    @Schema(example = "2")
    private Long orderId;

    @ArraySchema(schema = @Schema(implementation = OrderItemResponse.class))
    private List<OrderItemResponse> orderItemList;

    public static MyPageDetailResponse fromEntity(Orders order) {
        List<OrderItemResponse> orderItemResponses = order.getOrderItemList().stream()
                .map(OrderItemResponse::fromEntity)
                .collect(Collectors.toList());

        return MyPageDetailResponse.builder()
                .orderId(order.getId())
                .orderItemList(orderItemResponses)
                .build();
    }
}