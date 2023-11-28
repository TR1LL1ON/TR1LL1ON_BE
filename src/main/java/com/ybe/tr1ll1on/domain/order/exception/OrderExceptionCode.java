package com.ybe.tr1ll1on.domain.order.exception;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum OrderExceptionCode implements ExceptionCode {

    // 주문 관련 예외
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "404 Not Found", "주문을 찾을 수 없습니다."),

    // 주문 아이템 관련 예외
    ORDER_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "404 Not Found", "주문 상품을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String msg;
}