package com.ybe.tr1ll1on.domain.product.error;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ProductExceptionCode implements ExceptionCode {

    EMPTY_PRODUCT(HttpStatus.NOT_FOUND, "EMPTY_PRODUCT", "Product이 없습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
