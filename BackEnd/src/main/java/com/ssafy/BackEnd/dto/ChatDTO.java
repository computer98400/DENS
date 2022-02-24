package com.ssafy.BackEnd.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatDTO {
    private String roomId = "";
    private String username = "";
    private String user_no = "";
    private String message = "";
    private String dateTime = "";
    private String type = "";

}
