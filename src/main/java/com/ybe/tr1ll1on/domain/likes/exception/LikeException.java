package com.ybe.tr1ll1on.domain.likes.exception;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import com.ybe.tr1ll1on.global.exception.TrillionException;

public class LikeException extends TrillionException {

    public LikeException(ExceptionCode errorCode) {
        super(errorCode);
    }
}
