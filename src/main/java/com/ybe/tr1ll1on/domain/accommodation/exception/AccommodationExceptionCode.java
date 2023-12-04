package com.ybe.tr1ll1on.domain.accommodation.exception;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AccommodationExceptionCode implements ExceptionCode {

    // 숙소 관련 예외
    ACCOMMODATION_NOT_FOUND(HttpStatus.NOT_FOUND, "404 Not Found", "해당 숙소를 찾을 수 없습니다"),
    INVALID_PERSON_NUMBER(HttpStatus.BAD_REQUEST, "400 Bad Request", "인원 수는 1명 이상이어야 합니다");

    private final HttpStatus status;
    private final String code;
    private final String msg;

}
