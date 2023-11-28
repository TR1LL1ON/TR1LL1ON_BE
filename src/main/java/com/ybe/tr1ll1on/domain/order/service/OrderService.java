package com.ybe.tr1ll1on.domain.order.service;

import static com.ybe.tr1ll1on.domain.cart.exception.CartIdNotFoundExceptionCode.CARTID_NOT_FOUND;
import static com.ybe.tr1ll1on.domain.order.exception.OrderExceptionCode.INVALID_ORDER;
import static com.ybe.tr1ll1on.domain.order.exception.OrderExceptionCode.PRODUCT_SOLD_OUT;
import static com.ybe.tr1ll1on.domain.product.exception.ProductExceptionCode.EMPTY_PRODUCT;
import static com.ybe.tr1ll1on.domain.user.exception.InValidUserExceptionCode.USER_NOT_FOUND;
import static com.ybe.tr1ll1on.global.date.utill.DateUtil.isValidCheckInBetweenCheckOut;

import com.ybe.tr1ll1on.domain.cart.exception.CartIdNotFoundException;
import com.ybe.tr1ll1on.domain.cart.model.CartItem;
import com.ybe.tr1ll1on.domain.cart.repository.CartItemRepository;
import com.ybe.tr1ll1on.domain.order.exception.OrderException;
import com.ybe.tr1ll1on.domain.order.model.OrderItem;
import com.ybe.tr1ll1on.domain.order.model.Orders;
import com.ybe.tr1ll1on.domain.order.repository.OrderItemRepository;
import com.ybe.tr1ll1on.domain.order.repository.OrderRepository;
import com.ybe.tr1ll1on.domain.order.request.OrderItemRequest;
import com.ybe.tr1ll1on.domain.order.request.OrderRequest;
import com.ybe.tr1ll1on.domain.order.response.OrderResponse;
import com.ybe.tr1ll1on.domain.product.exception.ProductException;
import com.ybe.tr1ll1on.domain.product.model.Product;
import com.ybe.tr1ll1on.domain.product.model.ProductInfoPerNight;
import com.ybe.tr1ll1on.domain.product.repository.ProductInfoPerNightRepository;
import com.ybe.tr1ll1on.domain.product.repository.ProductRepository;
import com.ybe.tr1ll1on.domain.user.exception.InValidUserException;
import com.ybe.tr1ll1on.domain.user.model.User;
import com.ybe.tr1ll1on.domain.user.repository.UserRepository;
import com.ybe.tr1ll1on.security.util.SecurityUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductInfoPerNightRepository productInfoPerNightRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public OrderResponse order(OrderRequest orderRequest) {

        /* TODO 주문 처리 로직 */

        //1. 일단 해당 날짜에 주문이 가능한지 확인. -> 안되면? 예외 발생
        List<ProductInfoPerNight> allProductInfoList = isPossibleToOrder(orderRequest);

        //2. 모든 주문이 유효하다면, productInfoPerNight -> 해당 날짜에 관한것들 전부 count - 1
        processOrder(allProductInfoList);

        //3. 카트 아이템 제거
        deleteCartItem(orderRequest);


        //4. 주문 내역 생성 // TODO user 아직 안 넣음.
        Orders orderHistory = Orders.builder()
                .user(getUser())
                .totalPrice(orderRequest.getTotalPrice())
                .payment(orderRequest.getPayment())
                .orderCreateDate(LocalDateTime.now())
                .orderItemList(new ArrayList<>())
                .build();


        //5. 상품들 모두 주문 상품 레코드에 추가.
        for (OrderItemRequest oir : orderRequest.getOrders()) {
            orderHistory.getOrderItemList().add(
                OrderItem.builder()
                    .orders(orderHistory)
                    .personNumber(oir.getPersonNumber())
                    .price(oir.getPrice())
                    .startDate(oir.getCheckIn())
                    .endDate(oir.getCheckOut())
                    .product(getProduct(oir.getProductId()))
                    .reviewWritten(false)
                    .build()
            );
        }

        Orders saveOrder = orderRepository.save(orderHistory);
        List<OrderItem> saveOrderItemList = orderItemRepository.saveAll(
                orderHistory.getOrderItemList()
        );
        if (saveOrder == null || saveOrderItemList == null) {
            throw new OrderException(INVALID_ORDER);
        }
        return OrderResponse.of(saveOrder);
    }

    private void deleteCartItem(OrderRequest orderRequest) {
        for (OrderItemRequest or : orderRequest.getOrders()) {
            if (or.getCartId() == null) continue;
            CartItem cartItem = getCartItem(or.getCartId());
            cartItemRepository.delete(cartItem);
        }
    }

    /**
     * 요청에 들어온 주문들이 모두 가능한 주문인지 확인합니다.
     * 하나라도 틀린게 있다면 모든 주문은 취소됩니다.
     * @param orderRequest
     */
    private List<ProductInfoPerNight> isPossibleToOrder(OrderRequest orderRequest) {
        List<ProductInfoPerNight> allProductInfoList = new ArrayList<>();

        for (OrderItemRequest oir : orderRequest.getOrders()) {
            isValidCheckInBetweenCheckOut(oir.getCheckIn(), oir.getCheckOut());

            List<ProductInfoPerNight> productInfoPerNightList =
                    productInfoPerNightRepository.findByDateBetweenAndProductId(
                            oir.getCheckIn(), oir.getCheckOut().minusDays(1), oir.getProductId()
                    );

            for (ProductInfoPerNight pi : productInfoPerNightList) {
                if (pi.getCount() <= 0) {
                    //단 하루라도, 품절인 객실이 있다면 주문 전부 취소.
                    throw new OrderException(PRODUCT_SOLD_OUT);
                }
                allProductInfoList.add(pi);
            }
        }
        return allProductInfoList;
    }

    /**
     * 각 날짜마다 방의 갯수를 하나씩 줄입니다.
     * @param allProductInfoList
     */
    private void processOrder(List<ProductInfoPerNight> allProductInfoList) {
        for (ProductInfoPerNight pi : allProductInfoList) {
            pi.decreaseCountByOne();
        }
    }

    private Product getProduct(Long productId){
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(EMPTY_PRODUCT));
    }

    private User getUser() {
        return userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new InValidUserException(USER_NOT_FOUND));
    }

    private CartItem getCartItem(Long cartId) {
        return cartItemRepository.findById(cartId)
                .orElseThrow(() -> new CartIdNotFoundException(CARTID_NOT_FOUND));
    }


}
