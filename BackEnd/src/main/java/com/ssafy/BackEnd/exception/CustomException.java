package com.ssafy.BackEnd.exception;

import io.jsonwebtoken.JwtException;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
//@RequiredArgsConstructor
public class CustomException extends RuntimeException {

    private ErrorCode errorCode;

    public CustomException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
//    @ExceptionHandler(NullPointerException.class)
//    public void nullCheck() {
//        System.out.println("널포인터익셉션 발생");
//    }
//
//    @ExceptionHandler(NotFoundException.class)
//    public void notFound() {
//        System.out.println("아무것도 없음");
//    }
//
//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    public void notRequest() {
//        System.out.println("컨트롤러 매핑 확인");
//    }
//
//    @ExceptionHandler(JwtException.class)
//    public void jwtError() {
//        System.out.println("jwt 에러");
//    }

}
