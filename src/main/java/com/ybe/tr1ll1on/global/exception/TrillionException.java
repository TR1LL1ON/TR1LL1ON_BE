package com.ybe.tr1ll1on.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TrillionException extends RuntimeException {

    ExceptionCode errorCode;
    public TrillionException(ExceptionCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }
}
