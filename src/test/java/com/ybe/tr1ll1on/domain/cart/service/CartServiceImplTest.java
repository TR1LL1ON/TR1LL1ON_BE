package com.ybe.tr1ll1on.domain.cart.service;

import com.ybe.tr1ll1on.domain.cart.dto.request.AddCartItemRequest;
import com.ybe.tr1ll1on.domain.cart.dto.response.AddCartItemResponse;
import org.springframework.security.core.Authentication;
import com.ybe.tr1ll1on.domain.cart.model.Cart;
import com.ybe.tr1ll1on.domain.cart.model.CartItem;
import com.ybe.tr1ll1on.domain.cart.repository.CartItemRepository;
import com.ybe.tr1ll1on.domain.cart.repository.CartRepository;
import com.ybe.tr1ll1on.domain.product.model.Product;
import com.ybe.tr1ll1on.domain.product.repository.ProductRepository;
import com.ybe.tr1ll1on.domain.user.model.User;
import com.ybe.tr1ll1on.domain.user.repository.UserRepository;
import com.ybe.tr1ll1on.security.util.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addCartItemTest() {
        // 테스트에 필요한 더미 데이터 설정
        Long productId = 1L;
        Long userId = 2L;
        Long cartId = 3L;

        AddCartItemRequest request = new AddCartItemRequest();
        request.setProductId(productId);
        request.setCheckIn(LocalDate.now());
        request.setCheckOut(LocalDate.now().plusDays(1));
        request.setPersonNumber(2);
        request.setPrice(20000);

        Product product = Product.builder()
                .id(productId)
                .name("Sample Product")
                .checkInTime("14:00")
                .checkOutTime("12:00")
                .standardNumber(1)
                .maximumNumber(5)
                .count(10)
                .build();

        // User 객체 생성 및 필요한 데이터 초기화
        User user = User.builder()
                .id(userId)
                .cart(Cart.builder().id(cartId).user(user).build())
                .build();

        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUser(user);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Mock 설정
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(user.getCart()).thenReturn(cart);
        when(cartItemRepository.save(any())).thenAnswer(invocation -> {
            CartItem savedCartItem = invocation.getArgument(0);
            savedCartItem.setId(4L); // Mocked ID
            return savedCartItem;
        });

        // 테스트 실행
        AddCartItemResponse result = cartService.addCartItem(request);

        // 결과 검증
        assertEquals(productId, result.getProductId());
        assertEquals(request.getPersonNumber(), result.getPersonNumber());

    }


    @Test
    public void removeCartItemTest() {
        // 테스트에 필요한 더미 데이터 설정
        Long userId = 1L;
        Long cartItemIdToRemove = 2L;

        User user = new User();
        user.setId(userId);

        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemIdToRemove);
        cartItem.setCart(new Cart());
        cartItem.getCart().setUser(user);

        // Mock 설정
        when(SecurityUtil.getCurrentUserId()).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(cartItemRepository.findById(cartItemIdToRemove)).thenReturn(Optional.of(cartItem));

        // 테스트 실행
        cartService.removeCartItem(cartItemIdToRemove);

        // 결과 검증
        verify(cartItemRepository, times(1)).deleteById(cartItemIdToRemove);
    }
}
