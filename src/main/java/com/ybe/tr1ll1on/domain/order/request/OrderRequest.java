package com.ybe.tr1ll1on.domain.order.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ybe.tr1ll1on.domain.order.model.OrderItem;
import com.ybe.tr1ll1on.global.common.Payment;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @ArraySchema(schema = @Schema(implementation = OrderItemRequest.class))
    private List<OrderItemRequest> orders;

    @NotNull(message = "결제 수단을 입력하세요")
    @Schema(example = "KAKAOPAY")
    private Payment payment;
}
