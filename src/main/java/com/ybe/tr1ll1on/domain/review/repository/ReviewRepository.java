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
import java.time.LocalDate;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select avg(r.score) from Review r where r.product.accommodation.id = :accommodationId")
    Double getAvgReviewScore(@Param("accommodationId") Long accommodationId);

    @EntityGraph(attributePaths = {"user"})
    Optional<List<Review>> getReviewListByProductId(Long productId);

     @Query(value = "select r from Review r " +
            "left join fetch r.product p " +
            "left join fetch p.accommodation a " +
            "left join fetch r.orderItem oi " +
            "where r.user = :user and r.reviewDate between :startDate and CURRENT_DATE ",
            countQuery = "select count(r) from Review r where r.user = :user and r.reviewDate between :startDate and CURRENT_DATE")
    Page<Review> getReviewPageByUserWithDetailsAndDateRange(
            @Param("user") User user,
            @Param("startDate") LocalDate startDate,
            Pageable pageable);

     @Query(value = "select r from Review r " +
             "left join fetch r.user u " +
             "left join fetch r.product p " +
             "left join fetch p.accommodation a " +
             "where a.id = :accommodationId ",
             countQuery = "select count(r) from Review r left join r.product p left join p.accommodation a where a.id = :accommodationId ")
    Page<Review> getReviewPageByAccommodationWithDetails(
            @Param("accommodationId") Long accommodationId,
            Pageable pageable);
}