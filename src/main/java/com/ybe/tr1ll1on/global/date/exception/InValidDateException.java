package com.ybe.tr1ll1on.global.date.exception;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import com.ybe.tr1ll1on.global.exception.TrillionException;

public class InValidDateException extends TrillionException {

    public InValidDateException(ExceptionCode errorCode) {
        super(errorCode);
    }
}
