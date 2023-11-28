package com.ybe.tr1ll1on.security.exception;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import com.ybe.tr1ll1on.global.exception.TrillionException;

public class UserNotFoundException extends TrillionException {

    public UserNotFoundException(ExceptionCode errorCode) {
        super(SecurityExceptionCode.USER_NOT_FOUND);
    }
}
