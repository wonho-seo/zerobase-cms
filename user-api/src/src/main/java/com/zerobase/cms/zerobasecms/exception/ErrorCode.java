package com.zerobase.cms.zerobasecms.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    ALREADY_REGISTER_ACCOUNT(HttpStatus.BAD_REQUEST.value(), "이미 가입된 회원입니다."),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST.value(), "이미 가입된 회원입니다"),
    ALREADY_VERIFY(HttpStatus.BAD_REQUEST.value(), "이미 인증이 완료되었습니다."),
    WRONG_VERIFICATION(HttpStatus.BAD_REQUEST.value(), "잘못된 인증 시도입니다."),
    EXPIRE_CODE(HttpStatus.BAD_REQUEST.value(), "인증시도가 만료되었습니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST.value(), "패스워드가 일치하지 않습니다."),
    NOT_YET_VERIFICATION(HttpStatus.BAD_REQUEST.value(), "인증 되지않은 계정입니다..");

    private final int errorCode;
    private final String message;
}
