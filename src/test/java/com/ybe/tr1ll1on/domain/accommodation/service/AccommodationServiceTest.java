//package com.ybe.tr1ll1on.domain.accommodation.service;
//
//import com.ybe.tr1ll1on.domain.accommodation.dto.request.AccommodationRequest;
//import com.ybe.tr1ll1on.domain.accommodation.dto.response.AccommodationResponse;
//import com.ybe.tr1ll1on.domain.accommodation.repository.AccommodationMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class AccommodationServiceTest {
//
//    @Mock
//    private AccommodationMapper mapper;
//
//    @InjectMocks
//    private AccommodationService accommodationService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    @DisplayName("숙소 조회 성공 테스트")
//    public void testFindAccommodation() {
//
//        AccommodationRequest request = new AccommodationRequest();
//        request.setCheckIn(LocalDate.now());
//        request.setCheckOut(LocalDate.now().plusDays(1));
//        request.setPersonNumber(2);
//
//        List<AccommodationResponse> mockAccommodations = Arrays.asList(
//                AccommodationResponse.builder()
//                        .accommodationId(1L)
//                        .name("Hotel A")
//                        .imageUrl("image1.jpg")
//                        .address("Address 1")
//                        .areaCode("Area 1")
//                        .price(1)
//                        .build(),
//                AccommodationResponse.builder()
//                        .accommodationId(2L)
//                        .name("Hotel B")
//                        .imageUrl("image2.jpg")
//                        .address("Address 2")
//                        .areaCode("Area 2")
//                        .price(2)
//                        .build()
//        );
//
//        when(mapper.findAvailableAccommodation(request)).thenReturn(mockAccommodations);
//
//        List<AccommodationResponse> result = accommodationService.findAccommodation(request);
//
//        assertEquals(2, result.size());
//        assertEquals("Hotel A", result.get(0).getName());
//        assertEquals("image1.jpg", result.get(0).getImageUrl());
//        assertEquals("Address 1", result.get(0).getAddress());
//        assertEquals("Area 1", result.get(0).getAreaCode());
//        assertEquals(1, result.get(0).getPrice());
//
//        assertEquals("Hotel B", result.get(1).getName());
//        assertEquals("image2.jpg", result.get(1).getImageUrl());
//        assertEquals("Address 2", result.get(1).getAddress());
//        assertEquals("Area 2", result.get(1).getAreaCode());
//        assertEquals(2, result.get(1).getPrice());
//    }
//}