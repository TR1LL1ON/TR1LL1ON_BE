package com.ybe.tr1ll1on.domain.user.service;

import static com.ybe.tr1ll1on.domain.user.exception.InValidUserExceptionCode.USER_NOT_FOUND;

import com.ybe.tr1ll1on.domain.order.exception.OrderException;
import com.ybe.tr1ll1on.domain.order.exception.OrderExceptionCode;
import com.ybe.tr1ll1on.domain.order.model.Orders;
import com.ybe.tr1ll1on.domain.order.repository.OrderRepository;
import com.ybe.tr1ll1on.domain.user.dto.response.MyPageDetailResponse;
import com.ybe.tr1ll1on.domain.user.dto.response.MyPageResponse;
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

    @Transactional
    public List<MyPageResponse> getMyPage() {
        // 1. 현재 로그인한 사용자의 정보를 가져온다.
        User user = getUser();

        // 2. 해당 사용자의 주문 목록을 가져오면서 관련된 데이터들을 패치 조인을 통해 즉시 로딩한다.
        // 3. 관련된 데이터엔 상품 및 숙소에 대한 정보가 포함되어 있다.
        // 4. 숙소 이미지에 대한 정보는 배치 사이즈 설정을 통해 해당 개수만큼 즉시 로딩한다.
        List<Orders> orders = orderRepository.getUserOrdersWithDetails(user);

        // 5. 패치 조인을 통해 미리 가져온 주문 목록에 대한 정보를 MyPageListResponse 로 변환.
        List<MyPageResponse> myPageResponseList = orders.stream()
                .map(MyPageResponse::fromEntity)
                .collect(Collectors.toList());

        return myPageResponseList;
    }

    @Transactional
    public MyPageDetailResponse getMyPageDetails(Long orderId) {
        // 1. 현재 로그인한 사용자의 정보를 가져온다.
        User user = getUser();

        // 2. 해당 사용자의 특정 주문을 가져오면서 관련된 데이터들을 패치 조인을 통해 즉시 로딩한다.
        // 3. 관련된 데이터엔 상품 및 숙소에 대한 정보가 포함되어 있다.
        // 4. 상품 이미지에 대한 정보는 배치 사이즈 설정을 통해 해당 개수만큼 즉시 로딩한다.
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException(OrderExceptionCode.ORDER_NOT_FOUND));

        // 5. 패치 조인을 통해 미리 가져온 주문에 대한 정보를 MyPageDetailResponse 로 변환.
        MyPageDetailResponse myPageDetailResponse = MyPageDetailResponse.fromEntity(order);

        return myPageDetailResponse;
    }

    private User getUser() {
        return userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new InValidUserException(USER_NOT_FOUND));
    }
}