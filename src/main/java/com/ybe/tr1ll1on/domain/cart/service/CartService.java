package com.ybe.tr1ll1on.domain.cart.service;

import com.ybe.tr1ll1on.domain.cart.dto.AddCartItemRequest;
import com.ybe.tr1ll1on.domain.cart.dto.AddCartItemResponse;
import com.ybe.tr1ll1on.domain.cart.dto.RemoveCartItemResponse;
import com.ybe.tr1ll1on.domain.cart.dto.response.CartResponse;

import java.util.List;

public interface CartService {

    List<CartResponse> getAllCarts();

    AddCartItemResponse addCartItem(AddCartItemRequest request);

    RemoveCartItemResponse removeCartItem(Long cartItemId);

}
