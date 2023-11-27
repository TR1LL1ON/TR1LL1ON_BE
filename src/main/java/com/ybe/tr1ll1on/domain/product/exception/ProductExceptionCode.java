package com.ybe.tr1ll1on.domain.product.exception;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ProductExceptionCode implements ExceptionCode {

    EMPTY_PRODUCT(HttpStatus.NOT_FOUND, "EMPTY_PRODUCT", "상품 정보를 가져오지 못했습니다."),
    CHECKIN_EQUALS_CHECKOUT(HttpStatus.BAD_REQUEST, "CHECKIN_EQUALS_CHECKOUT", "체크인 날짜와 체크아웃 날짜가 동일합니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
