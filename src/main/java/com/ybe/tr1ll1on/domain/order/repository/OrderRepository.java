package com.ybe.tr1ll1on.domain.order.repository;

import com.ybe.tr1ll1on.domain.order.model.Orders;
import com.ybe.tr1ll1on.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    @Query("select distinct o " +
            "from Orders o " +
            "join fetch o.orderItemList oi " +
            "join fetch oi.product p " +
            "join fetch p.accommodation a " +
            "where o.user = :user")
    List<Orders> getUserOrdersWithDetails(@Param("user") User user);

    @Query("select o " +
            "from Orders o " +
            "join fetch o.orderItemList oi " +
            "join fetch oi.product p " +
            "join fetch p.accommodation a " +
            "where o.id = :orderId")
    Optional<Orders> findById(@Param("orderId") Long orderId);
}

/*
 * 1. 주문 내역과 연관된 엔티티들에 대한 패치 조인을 통해 N+1 문제를 최적화.
 * 2. 패치 조인을 사용하여 즉시 로딩으로 설정하며, 연관된 엔티티들을 한 번의 쿼리로 로딩.
 * 4. 주문 아이템 (OrderItem), 상품 (Product), 숙소 (Accommodation) 등 모든 연관 엔티티를 패치 조인.
 * 4. 주문 내역 (Orders) 조회할 때 관련된 엔티티들도 함께 로딩하므로 추가적인 쿼리가 발생하지 않음.
 * 5. 다만 패치 조인은 모든 관련 데이터를 함께 가져오기 때문에, 필요하지 않은 경우에는 성능 저하가 발생할 수 있음.
 */