package com.ybe.tr1ll1on.domain.product.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccomodationRequest {

    private LocalDate checkIn;

    private LocalDate checkOut;

    private Integer personNumber;
}
