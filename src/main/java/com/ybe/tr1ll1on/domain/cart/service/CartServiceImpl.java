package com.ybe.tr1ll1on.domain.cart.service;

import com.ybe.tr1ll1on.domain.cart.dto.*;
import com.ybe.tr1ll1on.domain.cart.error.CartIdNotFoundException;
import com.ybe.tr1ll1on.domain.cart.error.ProductNotExistException;
import com.ybe.tr1ll1on.domain.cart.error.UserAlreadyHasCartException;
import com.ybe.tr1ll1on.domain.cart.error.UserNotFoundException;
import com.ybe.tr1ll1on.domain.cart.model.Cart;
import com.ybe.tr1ll1on.domain.cart.model.CartItem;
import com.ybe.tr1ll1on.domain.cart.repository.CartItemRepository;
import com.ybe.tr1ll1on.domain.cart.repository.CartRepository;
import com.ybe.tr1ll1on.domain.product.model.Product;
import com.ybe.tr1ll1on.domain.product.repository.ProductRepository;
import com.ybe.tr1ll1on.domain.user.model.User;
import com.ybe.tr1ll1on.domain.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.ybe.tr1ll1on.domain.cart.error.CartIdNotFoundExceptionCode.CARTID_NOT_FOUND;
import static com.ybe.tr1ll1on.domain.cart.error.ProductNotExsitExceptionCode.PRODUCT_NOT_FOUND;
import static com.ybe.tr1ll1on.domain.cart.error.UserAlreadyHasCartExceptionCode.USER_ALREADY_HAS_CART;
import static com.ybe.tr1ll1on.domain.cart.error.UserNotFoundExceptionCode.USER_NOT_FOUND;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final EntityManager entityManager;
    private final UserRepository userRepository;


    @Override
    public GetCartResponse getAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        List<CartDto> cartDtos = carts.stream()
                .map(cart -> CartDto.builder()
                        .cartId(cart.getId())
                        .cartItems(cart.getCartItem().stream()
                                .map(cartItem -> CartItemDto.builder()
                                        .cartItemId(cartItem.getId())
                                        .productId(cartItem.getProduct().getId())
                                        .personNumber(cartItem.getPersonNumber())
                                        .price(cartItem.getPrice())
                                        .productName(cartItem.getProduct().getName())
                                        .checkInTime(cartItem.getProduct().getCheckInTime())
                                        .checkOutTime(cartItem.getProduct().getCheckOutTime())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
        return GetCartResponse.builder()
                .carts(cartDtos)
                .build();
    }

    @Override
    public AddCartItemResponse addCartItem(AddCartItemRequest request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotExistException(PRODUCT_NOT_FOUND));


        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));


        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
            user.setCart(cart);
        } else {
            // 이미 사용자가 장바구니를 가지고 있다면 추가를 막고 예외를 발생시킵니다.
            throw new UserAlreadyHasCartException(USER_ALREADY_HAS_CART);
        }

        // 장바구니 아이템을 생성하고 저장합니다.
        CartItem cartItem = new CartItem();
        cartItem.setCheckInTime(product.getCheckInTime());
        cartItem.setCheckOutTime(product.getCheckOutTime());
        cartItem.setProduct(product);
        cartItem.setCart(cart);
        cartItem.setPersonNumber(request.getPersonNumber());
        cartItemRepository.save(cartItem);

        // 응답을 생성하여 반환합니다.
        AddCartItemResponse response = new AddCartItemResponse();
        response.setCartItemId(cartItem.getId());
        response.setProductId(product.getId());
        response.setPersonNumber(cartItem.getPersonNumber());

        return response;
    }


    @Override
    @Transactional
    public RemoveCartItemResponse removeCartItem(Long cartId) {

        entityManager.createQuery("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId")
                .setParameter("cartId", cartId)
                .executeUpdate();


        cartRepository.deleteById(cartId);

        RemoveCartItemResponse response = new RemoveCartItemResponse();
        response.setCartId(cartId);

        return response;
    }




}
