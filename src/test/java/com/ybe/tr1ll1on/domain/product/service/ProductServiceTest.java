// package com.ybe.tr1ll1on.domain.product.service;
//
// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.when;
//
// import com.ybe.tr1ll1on.domain.accommodation.dto.request.AccommodationRequest;
// import com.ybe.tr1ll1on.domain.accommodation.model.Accommodation;
// import com.ybe.tr1ll1on.domain.accommodation.model.AccommodationFacility;
// import com.ybe.tr1ll1on.domain.accommodation.model.Category;
// import com.ybe.tr1ll1on.domain.accommodation.repository.AccommodationRepository;
// import com.ybe.tr1ll1on.domain.product.dto.response.AccommodationDetailResponse;
// import com.ybe.tr1ll1on.domain.product.dto.response.ProductFacilityResponse;
// import com.ybe.tr1ll1on.domain.product.dto.response.ProductResponse;
// import com.ybe.tr1ll1on.domain.product.model.Product;
// import com.ybe.tr1ll1on.domain.product.model.ProductFacility;
// import com.ybe.tr1ll1on.domain.product.repository.ProductInfoPerNightRepository;
// import com.ybe.tr1ll1on.domain.product.repository.ProductRepository;
// import java.time.LocalDate;
// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.Optional;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.springframework.boot.test.context.SpringBootTest;
//
// @SpringBootTest
// class ProductServiceTest {
//     @Mock
//     private AccommodationRepository accommodationRepository;
//
//     @Mock
//     private ProductRepository productRepository;
//
//     @Mock
//     private ProductInfoPerNightRepository productInfoPerNightRepository;
//
//     @InjectMocks
//     private ProductServiceImpl productService;
//
//     Accommodation accommodation1, accommodation2;
//     Product product1, product2;
//     ProductFacility productFacility;
//     AccommodationRequest accommodationRequest;
//     AccommodationDetailResponse accommodationDetailResponse;
//     ProductResponse productResponse;
//
//     @BeforeEach
//     void setUp() {
//         accommodation1 = Accommodation.builder()
//                 .name("플로팅웨일 설악도적폭포스테이")
//                 .category(Category.builder().categoryCode("B02010700").build())
//                 .images(new ArrayList<>())
//                 .facility(AccommodationFacility.builder()
//                         .hasCooking(true)
//                         .hasBeauty(true)
//                         .hasParking(true)
//                         .hasSauna(true)
//                         .hasSports(true).build())
//                 .build();
//         accommodation2 = Accommodation.builder()
//                 .name("자연닮은 치유농장")
//                 .category(Category.builder().categoryCode("B02010700").build())
//                 .images(new ArrayList<>())
//                 .facility(AccommodationFacility.builder()
//                         .hasCooking(true)
//                         .hasBeauty(true)
//                         .hasParking(true)
//                         .hasSauna(true)
//                         .hasSports(true).build())
//                 .build();
//         productFacility = ProductFacility.builder()
//                 .canCook(true).hasAirCondition(true).hasCable(true)
//                 .hasHairDryer(true).hasInternet(true).hasPc(false)
//                 .hasRefrigerator(true).hasSofa(false).hasTv(true)
//                 .hasTable(true).hasToiletries(true)
//                 .build();
//         product1 = Product.builder()
//                 .name("1층 스파룸(침실+한실)")
//                 .build();
//         product1.setProductFacility(productFacility);
//         product2 = Product.builder()
//                 .name("건강/마음/자연/치유")
//                 .build();
//         product2.setProductFacility(productFacility);
//         accommodationDetailResponse = AccommodationDetailResponse.builder()
//                 .name("플로팅웨일 설악도적폭포스테이")
//                 .build();
//         productResponse = ProductResponse.builder()
//                 .roomName("1층 스파룸(침실+한실)")
//                 .facility(ProductFacilityResponse.of(productFacility))
//                 .build();
//         accommodationRequest = new AccommodationRequest();
//         accommodationRequest.setCheckIn(LocalDate.now());
//         accommodationRequest.setCheckOut(LocalDate.now().plusDays(1));
//         accommodationRequest.setPersonNumber(2);
//     }
//
//     @Test
//     void 숙소_상세_조회_성공() {
//         //when
//         when(accommodationRepository.findById(any())).thenReturn(Optional.of(accommodation1));
//         when(productRepository.findByAccommodationAndMaximumNumberIsGreaterThanEqual(
//                 any(), any())).thenReturn(Collections.singletonList(product1));
//         when(productInfoPerNightRepository.findByDateBetweenAndProductId(
//                 any(), any(), any())).thenReturn(Collections.emptyList()); // 여기서 필요한 데이터 설정
//
//         AccommodationDetailResponse response = productService.getAccommodationDetail(
//                 1L, accommodationRequest
//         );
//
//         assertEquals(accommodationDetailResponse.getName(), response.getName());
//         assertEquals(productResponse.getRoomName(), response.getRooms().get(0).getRoomName());
//
//     }
//
//     @Test
//     void 숙소_상세_조회_실패() {
//         //when
//         when(accommodationRepository.findById(any())).thenReturn(Optional.of(accommodation2));
//         when(productRepository.findByAccommodationAndMaximumNumberIsGreaterThanEqual(
//                 any(), any())).thenReturn(Collections.singletonList(product2));
//         when(productInfoPerNightRepository.findByDateBetweenAndProductId(
//                 any(), any(), any())).thenReturn(Collections.emptyList()); // 여기서 필요한 데이터 설정
//
//         AccommodationDetailResponse response = productService.getAccommodationDetail(
//                 2L, accommodationRequest
//         );
//
//         assertNotEquals(accommodationDetailResponse.getName(), response.getName());
//         assertNotEquals(productResponse.getRoomName(), response.getRooms().get(0).getRoomName());
//     }
//
//     @Test
//     void 객실_상세_조회_성공() {
//         //when
//         when(accommodationRepository.findById(any())).thenReturn(Optional.of(accommodation1));
//         when(productRepository.findByAccommodationAndMaximumNumberIsGreaterThanEqual(
//                 any(), any())).thenReturn(Collections.singletonList(product1));
//         when(productInfoPerNightRepository.findByDateBetweenAndProductId(
//                 any(), any(), any())).thenReturn(Collections.emptyList()); // 여기서 필요한 데이터 설정
//         when(productRepository.findById(any())).thenReturn(Optional.of(product1));
//
//         ProductResponse response = productService.getProductDetail(
//                 1L, 1L, accommodationRequest
//         );
//
//         assertEquals(productResponse.getRoomName(), response.getRoomName());
//     }
//
//     @Test
//     void 객실_상세_조회_실패() {
//         //when
//         when(accommodationRepository.findById(any())).thenReturn(Optional.of(accommodation2));
//         when(productRepository.findByAccommodationAndMaximumNumberIsGreaterThanEqual(
//                 any(), any())).thenReturn(Collections.singletonList(product2));
//         when(productInfoPerNightRepository.findByDateBetweenAndProductId(
//                 any(), any(), any())).thenReturn(Collections.emptyList()); // 여기서 필요한 데이터 설정
//         when(productRepository.findById(any())).thenReturn(Optional.of(product2));
//
//         ProductResponse response = productService.getProductDetail(
//                 2L, 3L, accommodationRequest
//         );
//
//         assertNotEquals(productResponse.getRoomName(), response.getRoomName());
//     }
//
// }