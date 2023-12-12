package com.ybe.tr1ll1on.domain.likes.exception;

import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum LikeExceptionCode implements ExceptionCode {

    FAILED_LIKE(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR", "찜을 처리하지 못 했습니다."),
    LIKES_NOT_FOUND(HttpStatus.NOT_FOUND, "NOT FOUND", "찜 정보를 찾지 못했습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
