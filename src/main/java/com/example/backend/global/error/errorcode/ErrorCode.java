package com.example.backend.global.error.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_CREDENTIALS(UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다."),

    /* 403 FORBIDDEN : 권한이 없음 */
    INVALID_TOKEN(FORBIDDEN, "관리자 권한이 필요한 요청입니다. 접근 권한이 없습니다."),
    ACCESS_DENIED(FORBIDDEN, "관리자 권한이 필요한 요청입니다. 접근 권한이 없습니다."),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    USER_NOT_FOUND(NOT_FOUND, "유저를 찾을 수 없습니다"),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    USER_ALREADY_EXISTS(CONFLICT,"이미 가입된 사용자입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
