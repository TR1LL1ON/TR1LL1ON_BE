package com.ybe.tr1ll1on.domain.product.repository;

import com.ybe.tr1ll1on.domain.accommodation.model.Accommodation;
import com.ybe.tr1ll1on.domain.product.model.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p "
            + "left join fetch p.productFacility "
            + "left join fetch p.productImageList "
            + "WHERE p.accommodation = :accommodation AND p.maximumNumber >= :personNumber")
    List<Product> findByAccommodationAndMaximumNumberIsGreaterThanEqual(
            @Param("accommodation") Accommodation accommodation, @Param("personNumber") Integer personNumber
    );

    @Query("select p from Product p "
            + "left join fetch p.productFacility "
            + "left join fetch p.productImageList "
            + "where p.id = :productId")
    Optional<Product> findById(@Param("productId") Long productId);

    @Query("select p from Product p "
            + "left join fetch p.accommodation "
            + "where p.id = :productId")
    Optional<Product> findByIdSummary(@Param("productId") Long productId);

    @Query("select p from Product p left join fetch p.productImageList")
    List<Product> findAll();
}
