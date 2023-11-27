package com.ybe.tr1ll1on.global.date.exception;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
@Getter
public enum InValidDateExceptionCode implements ExceptionCode {
    CHECKIN_LATER_THEN_CHECKOUT(HttpStatus.BAD_REQUEST, "CHECKIN_LATER_THEN_CHECKOUT", "체크인 날짜가 체크아웃 날짜보다 늦습니다."),
    CHECKIN_EQUALS_CHECKOUT(HttpStatus.BAD_REQUEST, "CHECKIN_EQUALS_CHECKOUT", "체크인 날짜와 체크아웃 날짜가 동일합니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
