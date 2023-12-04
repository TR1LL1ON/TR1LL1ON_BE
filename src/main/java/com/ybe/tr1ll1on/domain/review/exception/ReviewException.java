package com.ybe.tr1ll1on.domain.review.exception;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import com.ybe.tr1ll1on.global.exception.TrillionException;

public class ReviewException extends TrillionException {
    public ReviewException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
