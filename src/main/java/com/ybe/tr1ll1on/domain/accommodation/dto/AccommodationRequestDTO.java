package com.ybe.tr1ll1on.domain.accommodation.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationRequestDTO {
    @NotNull
    LocalDate checkIn;
    @NotNull
    LocalDate checkOut;
    @NotNull
    @Min(message = "인원 수는 1명 이상이어야 합니다", value = 1L)
    Integer personNumber;
    String category;
    String areaCode;
}
