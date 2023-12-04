package com.ybe.tr1ll1on.domain.user.exception;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum InValidUserExceptionCode implements ExceptionCode {

    USER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "401 Unauthorized", "사용자를 찾을 수 없습니다. 다시 시도하십시오."),
    EMAIL_ALREADY_EXISTS(HttpStatus.UNAUTHORIZED, "401 Unauthorized", "이미 존재하는 이메일입니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
