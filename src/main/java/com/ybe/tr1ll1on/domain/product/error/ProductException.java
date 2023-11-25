package com.ybe.tr1ll1on.domain.product.error;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import com.ybe.tr1ll1on.global.exception.TrillionException;

public class ProductException extends TrillionException {
    public ProductException(ExceptionCode errorCode) {
        super(errorCode);
    }
}
