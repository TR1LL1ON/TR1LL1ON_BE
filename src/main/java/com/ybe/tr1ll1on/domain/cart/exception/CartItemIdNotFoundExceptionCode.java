package com.ybe.tr1ll1on.domain.cart.exception;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum CartItemIdNotFoundExceptionCode implements ExceptionCode {
    CART_ITEM_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "CART_ITEM_ID_NOT_FOUND", "장바구니상품ID를 찾을 수 없습니다");

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
