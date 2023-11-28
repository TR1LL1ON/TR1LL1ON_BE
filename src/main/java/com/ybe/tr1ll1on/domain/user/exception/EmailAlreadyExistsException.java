package com.ybe.tr1ll1on.domain.user.exception;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import com.ybe.tr1ll1on.global.exception.TrillionException;

public class EmailAlreadyExistsException extends TrillionException {
    public EmailAlreadyExistsException(ExceptionCode errorCode) {
        super(errorCode);
    }
}
