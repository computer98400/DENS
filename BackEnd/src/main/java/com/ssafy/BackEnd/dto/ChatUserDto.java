package com.ssafy.BackEnd.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
public class ChatUserDto {

    private Long profileId;

    private String name;

    @Builder
    public ChatUserDto(Long profileId, String name) {
        this.profileId = profileId;
        this.name = name;
    }


    public ChatUserDto() {

    }
}
