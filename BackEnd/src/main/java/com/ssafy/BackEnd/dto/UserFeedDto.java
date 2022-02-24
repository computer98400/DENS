package com.ssafy.BackEnd.dto;

import com.ssafy.BackEnd.entity.FileType;
import com.ssafy.BackEnd.entity.Profile;
import com.ssafy.BackEnd.entity.UserFeed;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@NoArgsConstructor
public class UserFeedDto {

    private Profile profile;

    @NotBlank
    private String content;

    private Map<FileType, List<MultipartFile>> userFeedFiles = new ConcurrentHashMap<>();

    @Builder
    public UserFeedDto(Profile profile, String content, Map<FileType, List<MultipartFile>> userFeedFiles) {
        this.profile = profile;
        this.content = content;
        this.userFeedFiles = userFeedFiles;
    }

    public UserFeed createUserFeed() {
        return UserFeed.builder()
                .profile(profile)
                .content(content)
                .userFeedFiles(new ArrayList<>())
                .build();
    }
}
