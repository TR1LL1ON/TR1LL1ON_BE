package com.ybe.tr1ll1on.domain.cart.error;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import com.ybe.tr1ll1on.global.exception.TrillionException;

public class CartIdNotFoundException extends TrillionException {
    public CartIdNotFoundException(ExceptionCode errorCode) {
        super(errorCode);
    }
}
