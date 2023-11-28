package com.ybe.tr1ll1on.domain.order.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ybe.tr1ll1on.global.common.Payment;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    @NotNull(message = "주문 상품을 채워주세요")
    private List<OrderItemRequest> orders;

    @NotNull(message = "결제 수단을 입력하세요")
    private Payment payment;

    @NotNull(message = "총 가격을 입력하세요")
    private Integer totalPrice;
}
