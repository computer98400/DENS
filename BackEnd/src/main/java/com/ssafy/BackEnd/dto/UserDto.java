package com.ssafy.BackEnd.dto;

import com.ssafy.BackEnd.entity.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor
@Data
public class UserDto {

    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    private LocalDateTime createDate;

    @Enumerated(EnumType.STRING)
    private UserIdentity userIdentity;

    @Builder
    public UserDto(String email, String name, String password, LocalDateTime createDate, UserIdentity userIdentity) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.createDate = createDate;
        this.userIdentity = userIdentity;
    }

    public User createUser() {
        return User.builder()
                .email(email)
                .name(name)
                .password(password)
                .createDate(LocalDateTime.now())
                .identity(userIdentity)
                .build();

    }
}
