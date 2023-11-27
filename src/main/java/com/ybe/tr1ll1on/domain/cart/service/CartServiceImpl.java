package com.ybe.tr1ll1on.domain.cart.service;

import com.ybe.tr1ll1on.domain.cart.dto.*;
import com.ybe.tr1ll1on.domain.cart.error.CartIdNotFoundException;
import com.ybe.tr1ll1on.domain.cart.error.ProductNotExistException;
import com.ybe.tr1ll1on.domain.cart.model.Cart;
import com.ybe.tr1ll1on.domain.cart.model.CartItem;
import com.ybe.tr1ll1on.domain.cart.repository.CartItemRepository;
import com.ybe.tr1ll1on.domain.cart.repository.CartRepository;
import com.ybe.tr1ll1on.domain.product.model.Product;
import com.ybe.tr1ll1on.domain.product.repository.ProductRepository;
import com.ybe.tr1ll1on.domain.user.model.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.ybe.tr1ll1on.domain.cart.error.CartIdNotFoundExceptionCode.CARTID_NOT_FOUND;
import static com.ybe.tr1ll1on.domain.cart.error.ProductNotExsitExceptionCode.PRODUCT_NOT_FOUND;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final EntityManager entityManager;


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

        // 사용자 정보가 없으므로, 사용자 ID만으로 간단히 사용자를 생성하여 사용
        User user = new User();
        user.setId(request.getUserId());

        // Check if the user has a cart, if not, create one
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
            user.setCart(cart);
        }

        CartItem cartItem = new CartItem();
        cartItem.setCheckInTime(product.getCheckInTime());
        cartItem.setCheckOutTime(product.getCheckOutTime());
        cartItem.setProduct(product);
        cartItem.setCart(cart);
        cartItem.setPersonNumber(request.getPersonNumber());

        cartItemRepository.save(cartItem);

        AddCartItemResponse response = new AddCartItemResponse();
        response.setCartItemId(cartItem.getId());
        response.setProductId(product.getId());
        response.setPersonNumber(cartItem.getPersonNumber());

        return response;
    }

    @Override
    @Transactional
    public RemoveCartItemResponse removeCartItem(Long cartId) {
        // 하위 엔티티인 CartItem을 먼저 삭제
        entityManager.createQuery("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId")
                .setParameter("cartId", cartId)
                .executeUpdate();

        // 상위 엔티티인 Cart를 삭제
        cartRepository.deleteById(cartId);

        RemoveCartItemResponse response = new RemoveCartItemResponse();
        response.setCartId(cartId);

        return response;
    }




}
