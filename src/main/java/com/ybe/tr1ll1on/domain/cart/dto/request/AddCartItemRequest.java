package com.ybe.tr1ll1on.domain.cart.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AddCartItemRequest {
    private Long productId;

    @NotNull(message = "인원 수를 입력하세요")
    private Integer personNumber;

    @NotNull(message = "체크인 날짜를 입력하세요")
    private LocalDate checkIn;

    @NotNull(message = "체크아웃 날짜를 입력하세요")
    private LocalDate checkOut;

    @NotNull(message = "가격을 입력하세요")
    private Integer price;

}
