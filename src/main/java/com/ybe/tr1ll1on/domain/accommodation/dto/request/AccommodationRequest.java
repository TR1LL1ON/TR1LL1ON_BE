package com.ybe.tr1ll1on.domain.accommodation.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
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
}
