package com.ybe.tr1ll1on.review.repository;

import com.ybe.tr1ll1on.domain.user.model.User;
import com.ybe.tr1ll1on.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findReviewsByUser(User user);
}
