package com.ybe.tr1ll1on.domain.user.service;

import static com.ybe.tr1ll1on.domain.user.exception.InValidUserExceptionCode.USER_NOT_FOUND;

import com.ybe.tr1ll1on.domain.likes.dto.response.LikeResponse;
import com.ybe.tr1ll1on.domain.likes.model.Likes;
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
import java.util.Collections;
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
        User user = getUser();

        List<Orders> orderList = getOrderListByUser(user);

        return orderList.stream()
                .map(MyPageResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public MyPageDetailResponse getMyPageDetails(Long orderId) {
        User user = getUser();

        Orders order = getOrdersById(orderId);

        return MyPageDetailResponse.fromEntity(order);
    }

    @Transactional(readOnly = true)
    public List<LikeResponse> getMyLikeList() {
        User user = getUser();
        log.info("마이페이지 찜 목록 조회!");

        List<Likes> likesList = user.getLikesList();
        // 등록한 날짜 역순으로 정렬 -> 가장 최근에 찜 누른 것부터 확인할 수 있도록 함.
        Collections.sort(likesList, (o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));

        return user.getLikesList()
                .stream().map(LikeResponse::fromEntity)
                .collect(Collectors.toList());

    }

    private User getUser() {
        return userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new InValidUserException(USER_NOT_FOUND));
    }

    private List<Orders> getOrderListByUser(User user) {
        return orderRepository.getOrderListByUser(user);
    }

    private Orders getOrdersById(Long orderId){
        return orderRepository.getOrdersByIdWithDetails(orderId)
                .orElseThrow(() -> new OrderException(OrderExceptionCode.ORDER_NOT_FOUND));
    }
}