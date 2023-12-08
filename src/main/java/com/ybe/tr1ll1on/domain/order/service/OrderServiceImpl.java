package com.ybe.tr1ll1on.domain.order.service;

import static com.ybe.tr1ll1on.domain.cart.exception.CartExceptionCode.CARTID_NOT_FOUND;
import static com.ybe.tr1ll1on.domain.order.exception.OrderExceptionCode.INVALID_ORDER;
import static com.ybe.tr1ll1on.domain.order.exception.OrderExceptionCode.PRODUCT_SOLD_OUT;
import static com.ybe.tr1ll1on.domain.product.exception.ProductExceptionCode.EMPTY_PRODUCT;
import static com.ybe.tr1ll1on.domain.user.exception.InValidUserExceptionCode.USER_NOT_FOUND;
import static com.ybe.tr1ll1on.global.date.util.DateUtil.isValidCheckInBetweenCheckOut;


import com.ybe.tr1ll1on.domain.cart.exception.CartException;
import com.ybe.tr1ll1on.domain.cart.model.CartItem;
import com.ybe.tr1ll1on.domain.cart.repository.CartItemRepository;
import com.ybe.tr1ll1on.domain.order.exception.OrderException;
import com.ybe.tr1ll1on.domain.order.model.OrderItem;
import com.ybe.tr1ll1on.domain.order.model.Orders;
import com.ybe.tr1ll1on.domain.order.repository.OrderItemRepository;
import com.ybe.tr1ll1on.domain.order.repository.OrderRepository;
import com.ybe.tr1ll1on.domain.order.dto.request.OrderItemRequest;
import com.ybe.tr1ll1on.domain.order.dto.request.OrderRequest;
import com.ybe.tr1ll1on.domain.order.dto.response.OrderResponse;
import com.ybe.tr1ll1on.domain.product.exception.ProductException;
import com.ybe.tr1ll1on.domain.product.model.Product;
import com.ybe.tr1ll1on.domain.product.model.ProductInfoPerNight;
import com.ybe.tr1ll1on.domain.product.repository.ProductInfoPerNightRepository;
import com.ybe.tr1ll1on.domain.product.repository.ProductRepository;
import com.ybe.tr1ll1on.domain.user.exception.InValidUserException;
import com.ybe.tr1ll1on.domain.user.model.User;
import com.ybe.tr1ll1on.domain.user.repository.UserRepository;
import com.ybe.tr1ll1on.global.common.ReviewStatus;
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
public class OrderServiceImpl implements OrderService {
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
            List<ProductInfoPerNight>[] allProductInfoList = isPossibleToOrder(orderRequest);

            //2. 모든 주문이 유효하다면, productInfoPerNight -> 해당 날짜에 관한것들 전부 count - 1
            processOrder(allProductInfoList);

            //3. 카트 아이템 제거
            deleteCartItem(orderRequest);

            //4. 주문 내역 생성
            Orders orderHistory = Orders.builder()
                    .user(getUser())
                    .payment(orderRequest.getPayment())
                    .orderCreateDate(LocalDateTime.now())
                    .orderItemList(new ArrayList<>())
                    .build();

            //5. 상품들 모두 주문 상품 레코드에 추가.
            int totalPrice = 0;
            for (int i = 0; i < orderRequest.getOrders().size(); i++) {
                OrderItemRequest oir = orderRequest.getOrders().get(i);

                // 각 요청마다 가격 계산
                int price = calculatePrice(allProductInfoList[i]);
                totalPrice += price; //총 가격

                orderHistory.getOrderItemList().add(
                        OrderItem.builder()
                                .orders(orderHistory)
                                .personNumber(oir.getPersonNumber())
                                .price(price)
                                .startDate(oir.getCheckIn())
                                .endDate(oir.getCheckOut())
                                .product(getProduct(oir.getProductId()))
                                .reviewStatus(ReviewStatus.NOT_WRITABLE)
                                .build()
                );
            }

            orderHistory.setTotalPrice(totalPrice); //총 가격 설정.
            Orders saveOrder = orderRepository.save(orderHistory);
            List<OrderItem> saveOrderItemList = orderItemRepository.saveAll(
                    orderHistory.getOrderItemList()
            );

            if (saveOrder == null || saveOrderItemList == null) {
                throw new OrderException(INVALID_ORDER);
            }

            log.info("OrderService : {}", saveOrder.getOrderCreateDate());
            return OrderResponse.of(saveOrder);
    }

    /**
     * 각 요청마다 가격을 계산합니다.
     * @param pir
     * @return
     */
    private int calculatePrice(List<ProductInfoPerNight> pir) {
        return pir.stream()
                .mapToInt(ProductInfoPerNight::getPrice)
                .sum();
    }

    /**
     * 만약 장바구니에서 들어온 요청일 경우, 카트에서 해당 장바구니 아이템을 삭제합니다.
     * @param orderRequest
     */
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
    private List<ProductInfoPerNight>[] isPossibleToOrder(OrderRequest orderRequest) {

        List<ProductInfoPerNight>[] allProductInfoList = new List[orderRequest.getOrders().size()];

        for (int i = 0; i < orderRequest.getOrders().size(); i++) {
            OrderItemRequest oir = orderRequest.getOrders().get(i);
            isValidCheckInBetweenCheckOut(oir.getCheckIn(), oir.getCheckOut());

            List<ProductInfoPerNight> productInfoPerNightList =
                    productInfoPerNightRepository.findByDateBetweenAndProductIdLock(
                            oir.getCheckIn(), oir.getCheckOut().minusDays(1),
                            getProduct(oir.getProductId())
                    );

            allProductInfoList[i] = new ArrayList<>();
            for (ProductInfoPerNight pi : productInfoPerNightList) {
                if (pi.getCount() <= 0) {
                    //단 하루라도, 품절인 객실이 있다면 주문 전부 취소.
                    throw new OrderException(PRODUCT_SOLD_OUT);
                }
                allProductInfoList[i].add(pi);
            }
        }
        return allProductInfoList;
    }

    /**
     * 각 날짜마다 방의 갯수를 하나씩 줄입니다.
     * @param allProductInfoList
     */
    private void processOrder(List<ProductInfoPerNight>[] allProductInfoList) {
        for (List<ProductInfoPerNight> pil : allProductInfoList) {
            for (ProductInfoPerNight pi : pil) {
                pi.decreaseCountByOne();
            }
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
                .orElseThrow(() -> new CartException(CARTID_NOT_FOUND));
    }


}
