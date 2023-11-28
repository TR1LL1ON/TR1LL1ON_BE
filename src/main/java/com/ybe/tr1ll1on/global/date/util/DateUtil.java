package com.ybe.tr1ll1on.global.date.util;

import static com.ybe.tr1ll1on.global.date.exception.InValidDateExceptionCode.CHECKIN_EQUALS_CHECKOUT;
import static com.ybe.tr1ll1on.global.date.exception.InValidDateExceptionCode.CHECKIN_LATER_THEN_CHECKOUT;

import com.ybe.tr1ll1on.global.date.exception.InValidDateException;
import com.ybe.tr1ll1on.global.date.exception.InValidDateExceptionCode;
import java.time.LocalDate;

public class DateUtil {
    public static void isValidCheckInBetweenCheckOut(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn.isEqual(checkOut)) {
            throw new InValidDateException(CHECKIN_EQUALS_CHECKOUT);
        }

        if (checkIn.isAfter(checkOut)) {
            throw new InValidDateException(CHECKIN_LATER_THEN_CHECKOUT);
        }
    }
}
