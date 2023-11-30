package com.ybe.tr1ll1on.domain.product.repository;

import com.ybe.tr1ll1on.domain.product.model.Product;
import com.ybe.tr1ll1on.domain.product.model.ProductInfoPerNight;
import jakarta.persistence.LockModeType;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInfoPerNightRepository extends JpaRepository<ProductInfoPerNight, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<ProductInfoPerNight> findByDateBetweenAndProductId(
            LocalDate checkIn, LocalDate checkOut, Long id
    );

    @Query("SELECT p FROM ProductInfoPerNight p WHERE p.date BETWEEN :checkIn AND :checkOut AND p.product = :product")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<ProductInfoPerNight> findByDateBetweenAndProductIdLock(
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut,
            @Param("product") Product product
    );
}
