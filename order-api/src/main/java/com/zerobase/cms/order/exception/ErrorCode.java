package com.zerobase.cms.order.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    NOT_FOUND_PRODUCT(HttpStatus.BAD_REQUEST.value(), "이미 가입된 회원입니다."),
    SAME_ITEM_NAME(HttpStatus.BAD_REQUEST.value(), "아이템 이름이 중복입니다."),
    NOT_FOUND_ITEM(HttpStatus.BAD_REQUEST.value(), "아이템 을 찾을수 없습니다."),
    CART_CHANGE_FAIL(HttpStatus.BAD_REQUEST.value(), "장바구니를 추가할수 없습니다." ),
    ITEM_COUNT_NOT_ENOUGH(HttpStatus.BAD_REQUEST.value(), "장바구니를 추가할수 없습니다." );
    private final int errorCode;
    private final String message;
}
