package com.ybe.tr1ll1on.domain.cart.service;

import com.ybe.tr1ll1on.domain.cart.dto.request.AddCartItemRequest;
import com.ybe.tr1ll1on.domain.cart.dto.response.AddCartItemResponse;
import com.ybe.tr1ll1on.domain.cart.dto.response.CartResponse;
import com.ybe.tr1ll1on.domain.cart.exception.CartException;
import com.ybe.tr1ll1on.domain.cart.model.Cart;
import com.ybe.tr1ll1on.domain.cart.model.CartItem;
import com.ybe.tr1ll1on.domain.cart.repository.CartItemRepository;
import com.ybe.tr1ll1on.domain.cart.repository.CartRepository;
import com.ybe.tr1ll1on.domain.product.exception.ProductException;
import com.ybe.tr1ll1on.domain.product.model.Product;
import com.ybe.tr1ll1on.domain.product.model.ProductImage;
import com.ybe.tr1ll1on.domain.product.repository.ProductRepository;
import com.ybe.tr1ll1on.domain.user.exception.InValidUserException;
import com.ybe.tr1ll1on.domain.user.exception.InValidUserExceptionCode;
import com.ybe.tr1ll1on.domain.user.model.User;
import com.ybe.tr1ll1on.domain.user.repository.UserRepository;
import com.ybe.tr1ll1on.security.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ybe.tr1ll1on.domain.cart.exception.CartExceptionCode.CARTID_NOT_FOUND;
import static com.ybe.tr1ll1on.domain.cart.exception.CartExceptionCode.CART_ITEM_ID_NOT_FOUND;
import static com.ybe.tr1ll1on.domain.product.exception.ProductExceptionCode.EMPTY_PRODUCT;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    // CartService
    @Override
    public List<CartResponse> getAllCarts() {
        User user = getUser();
        Cart cart = getCart(user);

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

        Product product = getProduct(request.getProductId());

        User user = getUser();
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
        User user = getUser();

        // 해당 cartItemId에 대한 유효성 검사
        CartItem cartItem = getCartItem(cartItemId);
        cartItemRepository.deleteById(cartItemId);
    }

    private Product getProduct(Long productId){
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(EMPTY_PRODUCT));
    }

    private User getUser() {
        return userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new InValidUserException(InValidUserExceptionCode.USER_NOT_FOUND));
    }

    private Cart getCart(User user) {
        Cart cart = cartRepository.findById(user.getCart().getId())
                .orElseThrow(() -> new CartException(CARTID_NOT_FOUND));
        return cart;
    }

    private CartItem getCartItem(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartException(CART_ITEM_ID_NOT_FOUND));
    }

}
