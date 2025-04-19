package com.example.backend.global.error.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    USER_ALREADY_EXISTS(CONFLICT,"이미 가입된 사용자입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
