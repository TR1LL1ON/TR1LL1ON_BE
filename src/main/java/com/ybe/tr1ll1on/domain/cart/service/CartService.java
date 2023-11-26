package com.ybe.tr1ll1on.domain.cart.service;

import com.ybe.tr1ll1on.domain.cart.dto.AddCartItemRequest;
import com.ybe.tr1ll1on.domain.cart.dto.AddCartItemResponse;
import com.ybe.tr1ll1on.domain.cart.dto.GetCartResponse;
import com.ybe.tr1ll1on.domain.cart.dto.RemoveCartItemResponse;

public interface CartService {

    GetCartResponse getAllCarts();

    AddCartItemResponse addCartItem(AddCartItemRequest request);

    RemoveCartItemResponse removeCartItem(Long cartItemId);

}
