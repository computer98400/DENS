package com.ssafy.BackEnd.util;

import com.ssafy.BackEnd.dto.UserFeedDto;
import com.ssafy.BackEnd.entity.FileType;
import com.ssafy.BackEnd.entity.Profile;
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
public class UserFeedAddForm {

    @NotBlank
    private String content;

    private List<MultipartFile> imageFiles;
    private List<MultipartFile> generalFiles;

    @Builder
    private UserFeedAddForm(String content, List<MultipartFile> imageFiles, List<MultipartFile> generalFiles) {
        this.content = content;
        this.imageFiles = (imageFiles != null) ? imageFiles : new ArrayList<>();
        this.generalFiles = (generalFiles != null) ? generalFiles : new ArrayList<>();
    }

    public UserFeedDto createUserFeedDto(Profile profile) {
        Map<FileType, List<MultipartFile>> userFeedFiles = getFileTypeListMap();
        return UserFeedDto.builder()
                .profile(profile)
                .content(content)
                .userFeedFiles(userFeedFiles)
                .build();
    }

    private Map<FileType, List<MultipartFile>> getFileTypeListMap() {
        Map<FileType, List<MultipartFile>> userFeedFiles = new ConcurrentHashMap<>();
        userFeedFiles.put(FileType.IMAGE, imageFiles);
        userFeedFiles.put(FileType.GENERAL, generalFiles);
        return userFeedFiles;
    }
}
