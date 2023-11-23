package com.ybe.tr1ll1on.domain.cart.error;

import com.ybe.tr1ll1on.global.exception.GlobalException;

public class InvalidCartItemException extends GlobalException {
    public InvalidCartItemException(String message) {
        super("장바구니에 추가하려는 상품이 유효하지 않습니다.");
    }
}
