package com.ssafy.BackEnd.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
//    public String handleRuntimeException(final RuntimeException e){
////        log.error("runtime error : {} ", e.getMessage());
////        return e.getMessage();
////    }
//
//    @ExceptionHandler(CustomException.class)
//    protected ResponseEntity<ErrorResponse> handleCustomException(final CustomException e) {
//        log.error("handleCustomException: {}", e.getErrorCode());
//        return ResponseEntity
//                .status(e.getErrorCode().getStatus().value())
//                .body(new ErrorResponse(e.getErrorCode()));
//    }

//    @ExceptionHandler(Exception.class)
//    protected ResponseEntity<ErrorResponse> handleException(final Exception e) {
//        log.error("handleException: {}", e.getMessage());
//        return ResponseEntity
//                .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus().value())
//                .body(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR));
//    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse response = ErrorResponse.of(ErrorCode.EXPIRED_CODE);
        response.setDetail(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
