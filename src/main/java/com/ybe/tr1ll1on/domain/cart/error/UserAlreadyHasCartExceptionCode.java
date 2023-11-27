package com.ybe.tr1ll1on.domain.cart.error;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum UserAlreadyHasCartExceptionCode implements ExceptionCode {
    USER_ALREADY_HAS_CART(HttpStatus.CONFLICT, "USER_ALREADY_HAS_CART", "사용자가 이미 장바구니가 있습니다");
    private final HttpStatus status;
    private final String code;
    private final String msg;
}
