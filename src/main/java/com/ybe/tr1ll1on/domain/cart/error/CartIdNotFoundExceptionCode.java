package com.ybe.tr1ll1on.domain.cart.error;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum CartIdNotFoundExceptionCode implements ExceptionCode {

    CARTID_NOT_FOUND(HttpStatus.NOT_FOUND, "CARTID_NOT_FOUND", "장바구니ID를 찾을 수 없습니다");

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
