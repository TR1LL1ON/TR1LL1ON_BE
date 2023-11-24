package com.ybe.tr1ll1on.domain.accommodation.repository;

import com.ybe.tr1ll1on.domain.accommodation.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryCode(String categoryCode);
}
