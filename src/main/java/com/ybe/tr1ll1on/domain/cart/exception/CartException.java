package com.ybe.tr1ll1on.domain.cart.exception;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import com.ybe.tr1ll1on.global.exception.TrillionException;

public class CartException extends TrillionException {

    public CartException(ExceptionCode errorCode) {
        super(errorCode);
    }
}
