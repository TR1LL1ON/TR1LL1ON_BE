package com.ybe.tr1ll1on.domain.order.exception;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import com.ybe.tr1ll1on.global.exception.TrillionException;

public class OrderNotFoundException extends TrillionException {
    public OrderNotFoundException (ExceptionCode errorCode) { super(errorCode); }
}