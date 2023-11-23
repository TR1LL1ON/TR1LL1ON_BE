package com.ybe.tr1ll1on.domain.cart.error;

import com.ybe.tr1ll1on.global.exception.GlobalException;

public class CartNotFoundException extends GlobalException {
    public CartNotFoundException(String message) {
        super("장바구니를 찾을 수 없습니다.");
    }
}
