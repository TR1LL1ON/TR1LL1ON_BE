package com.ybe.tr1ll1on.security.exception;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import com.ybe.tr1ll1on.global.exception.TrillionException;

public class SecurityException extends TrillionException {
    public SecurityException(ExceptionCode errorCode) { super(errorCode); }
}