package com.ybe.tr1ll1on.domain.cart.service;

import com.ybe.tr1ll1on.domain.cart.dto.AddCartItemResponse;
import com.ybe.tr1ll1on.domain.cart.dto.GetCartResponse;
import com.ybe.tr1ll1on.domain.cart.dto.RemoveCartItemResponse;

public interface CartService {

    GetCartResponse getAllCarts();

    AddCartItemResponse addCartItem(Long productId, Long userId);

    RemoveCartItemResponse removeCartItem(Long cartItemId);

}
