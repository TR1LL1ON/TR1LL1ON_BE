package com.ybe.tr1ll1on.domain.product.repository;

import com.ybe.tr1ll1on.domain.product.model.ProductInfoPerNight;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInfoPerNightRepository extends JpaRepository<ProductInfoPerNight, Long> {

    List<ProductInfoPerNight> findByDateBetweenAndProductId(LocalDate checkIn, LocalDate checkOut, Long id);
}
