package com.ybe.tr1ll1on.domain.cart.error;

import com.ybe.tr1ll1on.global.exception.GlobalException;

public class CartItemAlreadyExistsException extends GlobalException {
    public CartItemAlreadyExistsException(String message) {
        super("장바구니에 이미 존재하는 상품입니다.");
    }
}
