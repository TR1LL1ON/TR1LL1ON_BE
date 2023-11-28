//package com.ybe.tr1ll1on.domain.product.repository;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import com.ybe.tr1ll1on.domain.accommodation.model.Accommodation;
//import com.ybe.tr1ll1on.domain.accommodation.model.AccommodationFacility;
//import com.ybe.tr1ll1on.domain.accommodation.model.Category;
//import com.ybe.tr1ll1on.domain.product.model.Product;
//import com.ybe.tr1ll1on.domain.product.model.ProductFacility;
//import com.ybe.tr1ll1on.domain.product.model.ProductInfoPerNight;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//
//@DataJpaTest
//@DisplayName("객실 1박당 정보 테스트")
//class ProductInfoPerNightRepositoryTest {
//    @Autowired
//    private TestEntityManager entityManager; // 테스트용 엔티티 매니저
//
//    @Autowired
//    private ProductInfoPerNightRepository productInfoPerNightRepository;
//    Accommodation accommodation1;
//    Product product1, product2;
//    ProductFacility productFacility;
//    ProductInfoPerNight productInfoPerNight;
//    @BeforeEach
//    void setUp() {
//        productFacility = ProductFacility.builder()
//                .canCook(true).hasAirCondition(true).hasCable(true)
//                .hasHairDryer(true).hasInternet(true).hasPc(false)
//                .hasRefrigerator(true).hasSofa(false).hasTv(true)
//                .hasTable(true).hasToiletries(true)
//                .build();
//        product1 = Product.builder()
//                .name("1층 스파룸(침실+한실)")
//                .build();
//        product1.setProductFacility(productFacility);
//        product2 = Product.builder()
//                .name("건강/마음/자연/치유")
//                .build();
//        product2.setProductFacility(productFacility);
//        List<Product> list = new ArrayList<>();
//        list.add(product1);
//        accommodation1 = Accommodation.builder()
//                .name("플로팅웨일 설악도적폭포스테이")
//                .category(Category.builder().categoryCode("B02010700").build())
//                .images(new ArrayList<>())
//                .productList(list)
//                .facility(AccommodationFacility.builder()
//                        .hasCooking(true)
//                        .hasBeauty(true)
//                        .hasParking(true)
//                        .hasSauna(true)
//                        .hasSports(true).build())
//                .build();
//
//    }
//
//    @Test
//    void findByDateBetweenAndProductId_성공() {
//        //given
//        productInfoPerNight = ProductInfoPerNight.builder()
//                .product(product1).date(LocalDate.now())
//                .count(4)
//                .price(60000)
//                .build();
//        entityManager.persist(accommodation1);
//        entityManager.persist(product1);
//        entityManager.persist(productInfoPerNight);
//
//        //when
//        List<ProductInfoPerNight> find = productInfoPerNightRepository.findByDateBetweenAndProductId(
//                LocalDate.now(), LocalDate.now().plusDays(1), product1.getId()
//        );
//
//        //then
//        assertEquals(find.get(0).getId(), productInfoPerNight.getId());
//    }
//}