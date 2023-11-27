package com.ybe.tr1ll1on.security.exception;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import com.ybe.tr1ll1on.global.exception.TrillionException;
import com.ybe.tr1ll1on.global.exception.TrillionExceptionCode;

public class UserNotFoundException extends TrillionException {

    public UserNotFoundException(ExceptionCode errorCode) {
        super(TrillionExceptionCode.USER_NOT_FOUND);
    }
}
