package com.ybe.tr1ll1on.domain.accommodation.exception;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import com.ybe.tr1ll1on.global.exception.TrillionException;

public class AccommodationException extends TrillionException {

    public AccommodationException(ExceptionCode errorCode) {
        super(errorCode);
    }
}
