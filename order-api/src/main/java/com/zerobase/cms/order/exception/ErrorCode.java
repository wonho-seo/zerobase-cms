package com.zerobase.cms.order.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    NOT_FOUND_PRODUCT(HttpStatus.BAD_REQUEST.value(), "이미 가입된 회원입니다."),
    SAME_ITEM_NAME(HttpStatus.BAD_REQUEST.value(), "아이템 이름이 중복입니다.");
    private final int errorCode;
    private final String message;
}
