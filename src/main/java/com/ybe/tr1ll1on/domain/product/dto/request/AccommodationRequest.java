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
public class AccommodationRequest {

    @NotNull
    private LocalDate checkIn;

    @NotNull
    private LocalDate checkOut;

    @NotNull
    private Integer personNumber;

    public AccommodationRequest() {
        this.checkIn = LocalDate.now();
        this.checkOut = checkIn.plusDays(1);
        this.personNumber = 2;
    }
}
