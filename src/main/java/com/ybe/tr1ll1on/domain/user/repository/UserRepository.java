package com.ybe.tr1ll1on.domain.user.repository;

import com.ybe.tr1ll1on.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
