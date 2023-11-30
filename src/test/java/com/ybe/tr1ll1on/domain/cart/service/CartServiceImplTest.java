package com.ybe.tr1ll1on.domain.cart.service;

import com.ybe.tr1ll1on.domain.cart.dto.response.CartResponse;
import com.ybe.tr1ll1on.domain.cart.model.Cart;
import com.ybe.tr1ll1on.domain.cart.model.CartItem;
import com.ybe.tr1ll1on.domain.cart.repository.CartItemRepository;
import com.ybe.tr1ll1on.domain.cart.repository.CartRepository;
import com.ybe.tr1ll1on.domain.product.model.Product;
import com.ybe.tr1ll1on.domain.product.model.ProductImage;
import com.ybe.tr1ll1on.domain.product.repository.ProductRepository;
import com.ybe.tr1ll1on.domain.user.model.User;
import com.ybe.tr1ll1on.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {

    @InjectMocks
    private CartServiceImpl cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        // 새로운 SecurityContext를 생성하고 설정
        SecurityContext securityContextMock = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContextMock.getAuthentication()).thenReturn(authentication);

        // Creating a principal object directly
        org.springframework.security.core.userdetails.User principalUser =
                new org.springframework.security.core.userdetails.User(
                        "testUser", "testPassword", Collections.emptyList());
        when(authentication.getPrincipal()).thenReturn(principalUser);

        // 설정한 SecurityContext를 SecurityContextHolder에 넣어줍니다.
        SecurityContextHolder.setContext(securityContextMock);
    }

    @Test
    void getAllCarts() {
        // 테스트에 필요한 더미 데이터 설정
        Long userId = 1L;
        Long cartId = 2L;

        SecurityContext securityContextMock = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContextMock.getAuthentication()).thenReturn(authentication);

        // Creating a principal object directly
        org.springframework.security.core.userdetails.User principalUser =
                new org.springframework.security.core.userdetails.User(
                        "testUser", "testPassword", Collections.emptyList());
        when(authentication.getPrincipal()).thenReturn(principalUser);

        // 설정한 SecurityContext를 SecurityContextHolder에 넣어줍니다.
        SecurityContextHolder.setContext(securityContextMock);


        // User 객체 생성 및 필요한 데이터 초기화
        User user = new User();

        // Mock 설정
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // cart 객체를 mock으로 만들기
        Cart cart = mock(Cart.class);

        // cartRepository.findById(cartId) 메서드를 호출할 때 mock cart 객체 반환하도록 설정
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));

        // cart.getCartItem() 메서드를 호출할 때 원하는 값을 반환하도록 설정
        CartItem cartItem = mock(CartItem.class);
        when(cart.getCartItem()).thenReturn(Collections.singletonList(cartItem));

        // cartItem 객체의 getProduct() 메서드를 호출할 때 원하는 값을 반환하도록 설정
        Product product = mock(Product.class);
        when(cartItem.getProduct()).thenReturn(product);

        // product.getProductImageList() 메서드를 호출할 때 원하는 값을 반환하도록 설정
        ProductImage productImage = new ProductImage();
        when(product.getProductImageList()).thenReturn(Collections.singletonList(productImage));

        // 테스트 실행
        List<CartResponse> result = cartService.getAllCarts();

        // 결과 검증
        assertEquals(1, result.size());
        CartResponse cartResponse = result.get(0);
        assertEquals(cartItem.getId(), cartResponse.getCartItemId());
        assertEquals(product.getAccommodation().getId(), cartResponse.getAccommodationId());
        assertEquals(product.getId(), cartResponse.getProductId());
        assertEquals(product.getAccommodation().getName(), cartResponse.getAccommodationName());
        assertEquals(product.getAccommodation().getAddress(), cartResponse.getAccommodationAddress());
    }
}
