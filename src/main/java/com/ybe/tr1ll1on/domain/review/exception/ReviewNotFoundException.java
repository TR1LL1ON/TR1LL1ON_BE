package com.ybe.tr1ll1on.domain.review.exception;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import com.ybe.tr1ll1on.global.exception.TrillionException;

public class ReviewNotFoundException extends TrillionException {
    public ReviewNotFoundException(ExceptionCode errorCode) {
        super(errorCode);
    }
}
