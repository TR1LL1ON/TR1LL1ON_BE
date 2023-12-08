package com.ybe.tr1ll1on.domain.likes.repository;

import com.ybe.tr1ll1on.domain.likes.model.Likes;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByUserIdAndAccommodationId(Long userId, Long accommodationId);

}

