package com.ybe.tr1ll1on.domain.accommodation.dto.request;

import com.ybe.tr1ll1on.domain.accommodation.exception.AccommodationException;
import com.ybe.tr1ll1on.domain.accommodation.exception.AccommodationExceptionCode;
import com.ybe.tr1ll1on.global.date.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AccommodationRequest {

    LocalDate checkIn;

    LocalDate checkOut;

    Integer personNumber;

    String category;

    String areaCode;

    Integer pageSize;

    Integer maxId;

    public AccommodationRequest() {
        this(null, null, null);
    }

    public AccommodationRequest(LocalDate checkIn, LocalDate checkOut, Integer personNumber) {
        this(checkIn, checkOut, personNumber, null, null);
    }

    public AccommodationRequest(
            LocalDate checkIn, LocalDate checkOut, Integer personNumber, String category, String areaCode
    ) {
        this(checkIn, checkOut, personNumber, category, areaCode, null, null);
    }

    public AccommodationRequest(LocalDate checkIn, LocalDate checkOut, Integer personNumber, String category, String areaCode, Integer pageSize, Integer maxId) {
        this.checkIn = checkIn == null ? LocalDate.now() : checkIn;
        this.checkOut = checkOut == null ? LocalDate.now().plusDays(1) : checkOut;
        this.personNumber = personNumber == null ? 2 : personNumber;
        this.category = category;
        this.areaCode = areaCode;
        this.pageSize = pageSize == null ? 10 : pageSize;
        this.maxId = maxId;
        DateUtil.isValidCheckInBetweenCheckOut(this.checkIn, this.checkOut);
        if (this.personNumber < 1) {
            throw new AccommodationException(AccommodationExceptionCode.INVALID_PERSON_NUMBER);
        }
    }
}
