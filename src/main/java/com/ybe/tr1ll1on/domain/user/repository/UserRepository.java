package com.ybe.tr1ll1on.domain.user.repository;

import com.ybe.tr1ll1on.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("select u from User u left join fetch u.orderList where u.id = :userId")
    User getUserById(@Param("userId") Long userId);
}