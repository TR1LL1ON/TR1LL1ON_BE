package com.ybe.tr1ll1on.domain.cart.service;

import com.ybe.tr1ll1on.domain.cart.dto.request.AddCartItemRequest;
import com.ybe.tr1ll1on.domain.cart.dto.response.AddCartItemResponse;
import com.ybe.tr1ll1on.domain.cart.dto.response.CartResponse;
import com.ybe.tr1ll1on.domain.cart.exception.CartIdNotFoundException;
import com.ybe.tr1ll1on.domain.cart.exception.CartItemIdNotFoundException;
import com.ybe.tr1ll1on.domain.cart.exception.ProductNotExistException;
import com.ybe.tr1ll1on.domain.cart.exception.UserNotFoundException;
import com.ybe.tr1ll1on.domain.cart.model.Cart;
import com.ybe.tr1ll1on.domain.cart.model.CartItem;
import com.ybe.tr1ll1on.domain.cart.repository.CartItemRepository;
import com.ybe.tr1ll1on.domain.cart.repository.CartRepository;
import com.ybe.tr1ll1on.domain.product.model.Product;
import com.ybe.tr1ll1on.domain.product.model.ProductImage;
import com.ybe.tr1ll1on.domain.product.repository.ProductRepository;
import com.ybe.tr1ll1on.domain.user.model.User;
import com.ybe.tr1ll1on.domain.user.repository.UserRepository;
import com.ybe.tr1ll1on.security.util.SecurityUtil;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ybe.tr1ll1on.domain.cart.exception.CartIdNotFoundExceptionCode.CARTID_NOT_FOUND;
import static com.ybe.tr1ll1on.domain.cart.exception.CartItemIdNotFoundExceptionCode.CART_ITEM_ID_NOT_FOUND;
import static com.ybe.tr1ll1on.domain.cart.exception.ProductNotExsitExceptionCode.PRODUCT_NOT_FOUND;
import static com.ybe.tr1ll1on.domain.cart.exception.UserNotFoundExceptionCode.USER_NOT_FOUND;

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
                .map(cartItem -> {
                    Product product = cartItem.getProduct();
                    List<ProductImage> images = product.getProductImageList();

                    String imageUrl = images.isEmpty() ? "" : images.get(0).getImageUrl();


                    return CartResponse.builder()
                            .cartItemId(cartItem.getId())
                            .accommodationId(product.getAccommodation().getId())
                            .productId(product.getId())
                            .accommodationName(product.getAccommodation().getName())
                            .accommodationAddress(product.getAccommodation().getAddress())
                            .accommodationCategory(product.getAccommodation().getCategory().getCategoryCode())
                            .productName(product.getName())
                            .checkIn(cartItem.getStartDate())
                            .checkOut(cartItem.getEndDate())
                            .personNumber(cartItem.getPersonNumber())
                            .price(cartItem.getPrice())
                            .imageUrl(imageUrl)
                            .build();
                }).toList();
    }


    @Override
    public AddCartItemResponse addCartItem(AddCartItemRequest request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotExistException(PRODUCT_NOT_FOUND));

        Long userId = SecurityUtil.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));


        Cart cart = user.getCart();

        CartItem cartItem = CartItem.builder()
                .startDate(request.getCheckIn())
                .endDate(request.getCheckOut())
                .product(product)
                .personNumber(request.getPersonNumber())
                .price(request.getPrice())
                .cart(cart)
                .build();


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
    public void removeCartItem(Long cartItemId) {
        Long userId = SecurityUtil.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        // 해당 cartItemId에 대한 유효성 검사
        cartItemRepository.findById(cartItemId).orElseThrow(() -> new CartItemIdNotFoundException(CART_ITEM_ID_NOT_FOUND));
        cartItemRepository.deleteById(cartItemId);
    }

}
