package com.ssafy.BackEnd.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;


@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode implements EnumModel{

//    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
//    /*
//     * 404 NOT_FOUND: 리소스를 찾을 수 없음
//     */
//    POSTS_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글 정보를 찾을 수 없습니다."),
//
//    /*
//     * 405 METHOD_NOT_ALLOWED: 허용되지 않은 Request Method 호출
//     */
//    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메서드입니다."),
//
//    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다."),
//    ;

   //private final HttpStatus status;
    //private final String message;

    INVALID_CODE(400, "C001", "Invalid Code"),
    RESOURCE_NOT_FOUND(204, "C002", "Resource not found"),
    EXPIRED_CODE(400, "C003", "Expired Code"),
    AWS_ERROR(400, "A001", "aws client error"),
    INTERNER_SERVER_ERROR(500, "A005", "interner server error"),
    TEMPORARY_SERVER_ERROR(400, "T001", "오류 발생"),
    UNAUTH_USER_ERROR(400, "U00", "허가되지 않는 기능입니다"),
    INVALID_ID(400, "E00", "없는 아이디입니다"),
    NO_DATA_ERROR(400, "N00", "NULL DATA"),
    LOGIN_ERROR(400, "M01", "LOGIN ERROR"),
    SIGNUP_ERROR(400,  "M02", "SIGNUP ERROR"),
    EMAIL_ERROR(400, "M03", "EMAIL ERROR"),
    PASSWORD_VERIFY_ERROR(400, "M04", "PASSWORD VERIFY ERROR"),
    NOT_ADD_TEAMMEMBER(400, "T01", "CAN'T CREATE TEAMMEMBER"),
    ALREADY_EXISTS_CHATROOM(400, "C01", "CHATROOM IS ALREADY EXISTS"),
    CANNOT_CREATE_CHATROOM(400, "C02", "CANNOT CREATE CHATROOM"),
    NO_CHAT_ROOM(400, "C03", "NO CHAT ROOM");



    private int status;
    private String code;
    private String message;
    private String detail;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    @Override
    public String getKey() {
        return this.code;
    }

    @Override
    public String getValue() {
        return this.message;
    }

}
