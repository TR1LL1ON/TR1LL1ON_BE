package com.ybe.tr1ll1on.domain.order.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class OrderItemRequest {

    @NotNull(message = "체크인 날짜를 채워주세요")
    @Schema(example = "2023-11-24")
    private LocalDate checkIn;

    @NotNull(message = "체크아웃 날짜를 채워주세요")
    @Schema(example = "2023-11-26")
    private LocalDate checkOut;

    @NotNull(message = "인원 수를 채워주세요")
    @Schema(example = "2")
    private Integer personNumber;

    @NotNull(message = "주문 상품의 id를 채워주세요")
    @Schema(example = "5")
    private Long productId;

    private Long cartId;
    //장바구니 -> 주문하기 -> cartItemId = value
    //일반 숙소 상세 -> 주문하기 -> cartItemId = null
}
