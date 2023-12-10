package com.ybe.tr1ll1on.domain.review.repository;

import com.ybe.tr1ll1on.domain.review.model.Review;
import com.ybe.tr1ll1on.domain.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = "select r from Review r " +
            "left join fetch r.product p " +
            "left join fetch p.accommodation a " +
            "left join fetch r.orderItem oi " +
            "where r.user = :user",
            countQuery = "select count(r) from Review r where r.user = :user")
    Page<Review> getReviewsByUserWithDetails(@Param("user") User user, Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    Optional<List<Review>> getReviewsByProductId(Long productId);
}

/*
 * 1. 특정 사용자가 작성한 리뷰를 조회하며, 연관된 엔티티들에 대한 패치 조인을 통해 N+1 문제를 최적화.
 * 2. 패치 조인을 사용하여 관련 엔티티를 즉시 로딩하도록 설정하여, 한 번의 쿼리로 관련된 엔티티를 로딩한다.
 * 3. 모든 관련 엔티티에 대해 OrderItem, Product, Accommodation 등에 대해 패치 조인을 사용한다.
 * 4. Review 검색할 때 관련된 엔티티가 함께 로딩되므로 추가적인 쿼리가 발생하지 않는다.
 * 5. 그러나 패치 조인은 모든 관련 데이터를 함께 가져오기 때문에 필요하지 않은 경우 성능 저하가 발생할 수 있다.
 */