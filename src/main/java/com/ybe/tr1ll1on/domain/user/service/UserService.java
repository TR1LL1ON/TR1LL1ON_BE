package com.ybe.tr1ll1on.domain.user.service;

import static com.ybe.tr1ll1on.domain.user.exception.InValidUserExceptionCode.USER_NOT_FOUND;

import com.ybe.tr1ll1on.domain.order.exception.OrderException;
import com.ybe.tr1ll1on.domain.order.exception.OrderExceptionCode;
import com.ybe.tr1ll1on.domain.order.model.Orders;
import com.ybe.tr1ll1on.domain.order.repository.OrderRepository;
import com.ybe.tr1ll1on.domain.user.dto.response.MyPageDetailResponse;
import com.ybe.tr1ll1on.domain.user.dto.response.MyPageListResponse;
import com.ybe.tr1ll1on.domain.user.exception.InValidUserException;
import com.ybe.tr1ll1on.domain.user.model.User;
import com.ybe.tr1ll1on.domain.user.repository.UserRepository;
import com.ybe.tr1ll1on.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    /**
     * 사용자의 마이페이지 정보 ( 주문 정보가 담김 ) 를 가져오는 메서드.
     *
     * @return 현재 사용자의 주문 목록을 MyPageListResponse 로 변환한 리스트
     */
    @Transactional
    public List<MyPageListResponse> getMyPage() {

        // 사용자 정보를 가져오면서 주문 목록을 패치 조인을 통해 미리 로딩.
        // 패치 조인은 사용자와 연관된 주문들을 함께 로딩하여 N+1 쿼리 문제를 방지.
        User user = getUser();

        // 패치 조인을 통해 미리 가져온 주문 목록을 MyPageListResponse 로 변환.
        List<MyPageListResponse> myPageListResponse = user.getOrderList().stream()
                .map(MyPageListResponse::fromEntity)
                .collect(Collectors.toList());

        return myPageListResponse;
    }


    /**
     * 사용자의 특정 주문에 대한 주문 아이템을 가져오는 메서드. ( 예약한 객실 정보 )
     *
     * @param orderId 조회할 주문의 ID
     * @return 주문에 포함된 상품 목록을 MyPageDetailResponse 로 변환한 객체
     */
    @Transactional
    public MyPageDetailResponse getMyPageDetails(Long orderId) {
        Long userId = SecurityUtil.getCurrentUserId();
        User user = userRepository.getUserById(userId);

        // 주문 정보를 가져오면서 주문 아이템 목록을 패치 조인을 통해 미리 로딩.
        // 패치 조인은 주문과 연관된 주문 아이템들을 함께 로딩하여 N+1 쿼리 문제를 방지.
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(OrderExceptionCode.ORDER_NOT_FOUND));

        return MyPageDetailResponse.fromEntity(order);
    }

    private User getUser() {
        return userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new InValidUserException(USER_NOT_FOUND));
    }

}