package com.ybe.tr1ll1on.domain.user.dto.response;

import com.ybe.tr1ll1on.domain.order.model.Orders;
import com.ybe.tr1ll1on.domain.order.response.OrderItemResponse;
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
    private Long orderId;
    private List<OrderItemResponse> orderItemList;

    public static MyPageDetailResponse fromEntity(Orders order) {
        // 패치 조인을 통해 미리 가져온 주문 아이템 목록을 orderItemResponses 로 변환.
        List<OrderItemResponse> orderItemResponses = order.getOrderItemList().stream()
                .map(OrderItemResponse::of)
                .collect(Collectors.toList());

        return MyPageDetailResponse.builder()
                .orderId(order.getId())
                .orderItemList(orderItemResponses)
                .build();
    }
}