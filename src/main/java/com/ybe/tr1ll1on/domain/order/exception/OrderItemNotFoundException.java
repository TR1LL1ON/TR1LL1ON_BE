package com.ybe.tr1ll1on.domain.order.exception;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import com.ybe.tr1ll1on.global.exception.TrillionException;

public class OrderItemNotFoundException extends TrillionException {
    public OrderItemNotFoundException (ExceptionCode errorCode) { super(errorCode); }
}
