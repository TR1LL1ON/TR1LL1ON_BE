package com.ybe.tr1ll1on.domain.order.exception;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum OrderExceptionCode implements ExceptionCode {

    INVALID_ORDER(HttpStatus.INTERNAL_SERVER_ERROR, "INVALID_ORDER", "주문 처리하지 못 했습니다."),
    PRODUCT_SOLD_OUT(HttpStatus.NOT_FOUND, "PRODUCT_SOLD_OUT", "주문 상품이 품절입니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER_NOT_FOUND", "주문 정보를 찾지 못했습니다."),
    ORDER_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER_ITEM_NOT_FOUND", "주문 상품 정보를 찾지 못했습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
