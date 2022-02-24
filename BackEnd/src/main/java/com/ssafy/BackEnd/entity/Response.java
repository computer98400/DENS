package com.ssafy.BackEnd.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class Response {
    private String response;
    private String message;
    private Object data;

    public Response(String response, String message, Object data) {
    }
}
