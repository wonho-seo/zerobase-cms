package com.zerobase.cms.zerobasecms.exception;

import javax.validation.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> customExceptionHandler(final CustomException e) {
        log.warn("api : {},", e.getErrorCode());
        return ResponseEntity.status(e.getErrorCode().getErrorCode())
            .body(new ExceptionResponse(e.getMessage(), e.getErrorCode().getErrorCode()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionResponse> validationExceptionHandler(final ValidationException e){
        log.warn("api : {},", e.getMessage());
        return ResponseEntity.badRequest()
            .body(new ExceptionResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }


    @Getter
    @ToString
    @AllArgsConstructor
    public static class ExceptionResponse {

        private String message;
        private int code;
    }
}
