package com.ybe.tr1ll1on.domain.cart.service;

import com.ybe.tr1ll1on.domain.cart.dto.request.AddCartItemRequest;
import com.ybe.tr1ll1on.domain.cart.dto.response.AddCartItemResponse;
import com.ybe.tr1ll1on.domain.cart.dto.response.CartResponse;

import java.util.List;

public interface CartService {

    List<CartResponse> getAllCarts();

    AddCartItemResponse addCartItem(AddCartItemRequest request);

    void removeCartItem(Long cartItemId);

}
