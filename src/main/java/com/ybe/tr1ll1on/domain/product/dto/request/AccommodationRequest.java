package com.ybe.tr1ll1on.domain.product.dto.request;

import com.ybe.tr1ll1on.global.date.util.DateUtil;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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

    public AccommodationRequest(LocalDate checkIn, LocalDate checkOut, Integer personNumber) {
        this.checkIn = checkIn == null ? LocalDate.now() : checkIn;
        this.checkOut = checkOut == null ? LocalDate.now().plusDays(1) : checkOut;
        this.personNumber = personNumber == null ? 2 : personNumber;

        DateUtil.isValidCheckInBetweenCheckOut(
                this.checkIn, this.checkOut
        );
    }
}
