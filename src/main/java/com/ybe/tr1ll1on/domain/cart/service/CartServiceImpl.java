package com.ybe.tr1ll1on.domain.cart.service;

import com.ybe.tr1ll1on.domain.cart.dto.AddCartItemRequest;
import com.ybe.tr1ll1on.domain.cart.dto.AddCartItemResponse;
import com.ybe.tr1ll1on.domain.cart.dto.RemoveCartItemResponse;
import com.ybe.tr1ll1on.domain.cart.dto.response.CartResponse;
import com.ybe.tr1ll1on.domain.cart.error.CartIdNotFoundException;
import com.ybe.tr1ll1on.domain.cart.error.ProductNotExistException;
import com.ybe.tr1ll1on.domain.cart.error.UserNotFoundException;
import com.ybe.tr1ll1on.domain.cart.model.Cart;
import com.ybe.tr1ll1on.domain.cart.model.CartItem;
import com.ybe.tr1ll1on.domain.cart.repository.CartItemRepository;
import com.ybe.tr1ll1on.domain.cart.repository.CartRepository;
import com.ybe.tr1ll1on.domain.product.model.Product;
import com.ybe.tr1ll1on.domain.product.repository.ProductRepository;
import com.ybe.tr1ll1on.domain.user.model.User;
import com.ybe.tr1ll1on.domain.user.repository.UserRepository;
import com.ybe.tr1ll1on.security.util.SecurityUtil;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ybe.tr1ll1on.domain.cart.error.CartIdNotFoundExceptionCode.CARTID_NOT_FOUND;
import static com.ybe.tr1ll1on.domain.cart.error.ProductNotExsitExceptionCode.PRODUCT_NOT_FOUND;
import static com.ybe.tr1ll1on.domain.cart.error.UserNotFoundExceptionCode.USER_NOT_FOUND;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final EntityManager entityManager;
    private final UserRepository userRepository;


    // CartService
    @Override
    public List<CartResponse> getAllCarts() {
        Long userId = SecurityUtil.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        Cart cart = cartRepository.findById(user.getCart().getId())
                .orElseThrow(() -> new CartIdNotFoundException(CARTID_NOT_FOUND));

        return cart.getCartItem().stream()
                .map(cartItem -> CartResponse.builder()
                        .cartItemId(cartItem.getId())
                        .accommodationName(cartItem.getProduct().getAccommodation().getName())
                        .accommodationAddress(cartItem.getProduct().getAccommodation().getAddress())
                        .accommodationCategory(cartItem.getProduct().getAccommodation().getCategory().getCategoryCode())
                        .productName(cartItem.getProduct().getName())
                        .checkIn(cartItem.getStartDate())
                        .checkOut(cartItem.getEndDate())
                        .personNumber(cartItem.getPersonNumber())
                        .price(cartItem.getPrice())
                        .build()
                ).toList();
    }


    @Override
    public AddCartItemResponse addCartItem(AddCartItemRequest request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotExistException(PRODUCT_NOT_FOUND));

        Long userId = SecurityUtil.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));


        Cart cart = user.getCart();
//        if (cart == null) {
//        } else {
//            // 이미 사용자가 장바구니를 가지고 있다면 추가를 막고 예외를 발생시킵니다.
//            throw new UserAlreadyHasCartException(USER_ALREADY_HAS_CART);
//        }
        // 장바구니 아이템을 생성하고 저장합니다.
        CartItem cartItem = new CartItem();
        cartItem.setStartDate(request.getCheckIn());
        cartItem.setEndDate(request.getCheckOut());
        cartItem.setProduct(product);
        cartItem.setCart(cart);
        cartItem.setPersonNumber(request.getPersonNumber());
        cartItem.setPrice(request.getPrice());

        CartItem saved = cartItemRepository.save(cartItem);

        // 응답을 생성하여 반환합니다.
        AddCartItemResponse response = new AddCartItemResponse();
        response.setCartItemId(saved.getId());
        response.setProductId(saved.getProduct().getId());
        response.setPersonNumber(saved.getPersonNumber());

        return response;
    }

    @Override
    @Transactional
    public RemoveCartItemResponse removeCartItem(Long cartId) {
        // 해당 cartId에 대한 유효성 검사
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartIdNotFoundException(CARTID_NOT_FOUND));

        // CartItem 삭제
        entityManager.createQuery("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId")
                .setParameter("cartId", cartId)
                .executeUpdate();

        // Cart 삭제
        cartRepository.deleteById(cartId);

        RemoveCartItemResponse response = new RemoveCartItemResponse();
        response.setCartId(cartId);

        return response;
    }


}
