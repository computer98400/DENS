package com.ssafy.BackEnd.dto;

import com.ssafy.BackEnd.entity.User;
import com.ssafy.BackEnd.entity.UserIdentity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

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
