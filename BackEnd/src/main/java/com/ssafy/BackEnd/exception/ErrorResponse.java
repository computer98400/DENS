package com.ssafy.BackEnd.exception;

import com.ssafy.BackEnd.exception.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {

//    private final LocalDateTime timestamp = LocalDateTime.now();
//    private final int status;
//    private final String error;
//    private final String code;
//    private final String message;
//
//    public ErrorResponse(ErrorCode errorCode) {
//        this.status = errorCode.getStatus().value();
//        this.error = errorCode.getStatus().name();
//        this.code = errorCode.name();
//        this.message = errorCode.getMessage();
//    }

    private String message;
    private String code;
    private int status;
    private String detail;

    public ErrorResponse(ErrorCode code) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
        this.detail = code.getDetail();
    }

    public static ErrorResponse of (ErrorCode code) {
        return new ErrorResponse(code);
    }

}
