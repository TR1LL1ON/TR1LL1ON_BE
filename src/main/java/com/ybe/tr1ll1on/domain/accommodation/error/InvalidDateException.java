package com.ybe.tr1ll1on.domain.accommodation.error;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import com.ybe.tr1ll1on.global.exception.TrillionException;

public class InvalidDateException extends TrillionException {
    public InvalidDateException(ExceptionCode errorCode) {
        super(errorCode);
    }
}
