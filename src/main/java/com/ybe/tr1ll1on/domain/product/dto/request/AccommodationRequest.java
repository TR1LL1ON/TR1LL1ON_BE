package com.ybe.tr1ll1on.domain.product.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationRequest {

    @NotNull(message = "체크인 날짜를 입력하세요")
    private LocalDate checkIn;

    @NotNull(message = "체크아웃 날짜를 입력하세요")
    private LocalDate checkOut;

    @NotNull(message = "인원 수를 입력하세요")
    private Integer personNumber;
}
