package com.ybe.tr1ll1on.domain.accommodation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybe.tr1ll1on.domain.accommodation.dto.request.AccommodationRequest;
import com.ybe.tr1ll1on.domain.accommodation.dto.response.AccommodationResponse;
import com.ybe.tr1ll1on.domain.accommodation.model.Accommodation;
import com.ybe.tr1ll1on.domain.accommodation.service.AccommodationService;
import com.ybe.tr1ll1on.global.date.exception.InValidDateException;
import com.ybe.tr1ll1on.global.date.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Collections;

import static com.ybe.tr1ll1on.global.date.exception.InValidDateExceptionCode.CHECKIN_EQUALS_CHECKOUT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccommodationControllerTest {

    @InjectMocks
    AccommodationController accommodationController;

    @Mock
    private AccommodationService accommodationService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(accommodationController).build();
    }

    @Test
    @DisplayName("숙소 전체 조회 컨트롤러 성공 테스트")
    public void getAll() throws Exception {

        // Mock 데이터 설정
        LocalDate checkInDate = LocalDate.now();
        LocalDate checkOutDate = LocalDate.now().plusDays(1);

        AccommodationRequest accommodationRequest = new AccommodationRequest();
        accommodationRequest.setCheckIn(checkInDate);
        accommodationRequest.setCheckOut(checkOutDate);
        accommodationRequest.setPersonNumber(2);

        AccommodationResponse accommodationResponse = new AccommodationResponse();

        given(accommodationService.findAccommodation(accommodationRequest))
                .willReturn(Collections.singletonList(accommodationResponse));

        mockMvc.perform(get("/products")
                        .param("checkIn", String.valueOf(accommodationRequest.getCheckIn()))
                        .param("checkOut", String.valueOf(accommodationRequest.getCheckOut()))
                        .param("personNumber", String.valueOf(accommodationRequest.getPersonNumber()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

//    @Test
//    @DisplayName("숙소 전체 조회 컨트롤러 실패 테스트")
//    public void getAll_실패() throws Exception {
//
//        // Mock 데이터 설정
//        LocalDate checkInDate = LocalDate.now();
//        LocalDate checkOutDate = LocalDate.now();
//
//        AccommodationRequest accommodationRequest = new AccommodationRequest();
//        accommodationRequest.setCheckIn(checkInDate);
//        accommodationRequest.setCheckOut(checkOutDate);
//        accommodationRequest.setPersonNumber(2);
//
//        AccommodationResponse accommodationResponse = new AccommodationResponse();
//
//        given(accommodationService.findAccommodation(accommodationRequest))
//                .willReturn(Collections.singletonList(accommodationResponse));
//
//        mockMvc.perform(get("/products")
//                        .param("checkIn", String.valueOf(accommodationRequest.getCheckIn()))
//                        .param("checkOut", String.valueOf(accommodationRequest.getCheckOut()))
//                        .param("personNumber", String.valueOf(accommodationRequest.getPersonNumber()))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print());
//
//    }
}