package com.ybe.tr1ll1on.domain.cart.exception;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import com.ybe.tr1ll1on.global.exception.TrillionException;

public class ProductNotExistException extends TrillionException {
    public ProductNotExistException(ExceptionCode errorCode) {
        super(errorCode);
    }
}
