package com.ybe.tr1ll1on.domain.accommodation.dto.request;

import com.ybe.tr1ll1on.global.date.util.DateUtil;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class AccommodationRequest {
    @NotNull
    LocalDate checkIn;

    @NotNull
    LocalDate checkOut;

    @NotNull
    @Min(message = "인원 수는 1명 이상이어야 합니다", value = 1L)
    Integer personNumber;

    String category;

    String areaCode;

    public AccommodationRequest() {
        this.checkIn = LocalDate.now();
        this.checkOut = checkIn.plusDays(1);
        this.personNumber = 2;
    }
    public AccommodationRequest(LocalDate checkIn, LocalDate checkOut, Integer personNumber) {
        this.checkIn = checkIn == null ? LocalDate.now() : checkIn;
        this.checkOut = checkOut == null ? LocalDate.now().plusDays(1) : checkOut;
        this.personNumber = personNumber == null ? 2 : personNumber;

        DateUtil.isValidCheckInBetweenCheckOut(
                this.checkIn, this.checkOut
        );
    }

    public AccommodationRequest(
            LocalDate checkIn, LocalDate checkOut, Integer personNumber,
            String category, String areaCode
    ) {
        this.checkIn = checkIn == null ? LocalDate.now() : checkIn;
        this.checkOut = checkOut == null ? LocalDate.now().plusDays(1) : checkOut;
        this.personNumber = personNumber == null ? 2 : personNumber;
        this.category = category;
        this.areaCode = areaCode;

        DateUtil.isValidCheckInBetweenCheckOut(
                this.checkIn, this.checkOut
        );
    }
}
