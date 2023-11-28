package com.ybe.tr1ll1on.domain.accommodation.repository;

import com.ybe.tr1ll1on.domain.accommodation.model.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    // Accommodation 엔터티와 그에 연관된 productList를 함께 로딩하면서,
    // id가 주어진 accommodationId와 일치하는 Accommodation 엔터티를 선택하는 쿼리
    @Query("select a from Accommodation a left join fetch a.productList where a.id = :accommodationId")
    Optional<Accommodation> findById(@Param("accommodationId") Long accommodationId);

    /*
    1. Accommodation 엔티티를 가져올 때, 해당 엔티티와 연관된 Product 엔티티 리스트는 데이터베이스에서 로딩되지 않는다. (Fetch Type = LAZY)
    2. 이후 접근할 때마다 데이터베이스에서 추가적인 쿼리가 실행되어 각각의 Product 엔티티를 가져오게 된다.
    3. 이로 인해 총 N+1 번의 쿼리가 실행되는 문제가 발생한다. (Accommodation 엔티티를 가져오는 1번의 쿼리 + Product 엔티티 리스트를 가져오는 N번의 쿼리)
    */

    /*
    fetch join을 사용하게 되면 Product 엔티티 리스트를 함께 로딩하므로,
    Accommodation 엔티티를 가져올 때 추가적인 쿼리 없이 한 번의 쿼리로 필요한 데이터를 모두 가져올 수 있다.
    */
}