package com.ybe.tr1ll1on.security.exception;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import com.ybe.tr1ll1on.global.exception.TrillionException;
import com.ybe.tr1ll1on.global.exception.TrillionExceptionCode;

public class InvalidTokenException extends TrillionException {

    public InvalidTokenException(ExceptionCode errorCode) {
        super(TrillionExceptionCode.INVALID_TOKEN);
    }
}
