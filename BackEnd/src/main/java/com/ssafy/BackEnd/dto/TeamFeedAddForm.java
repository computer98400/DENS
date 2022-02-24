package com.ssafy.BackEnd.dto;

import com.ssafy.BackEnd.entity.FileType;
import com.ssafy.BackEnd.entity.Profile;
import com.ssafy.BackEnd.entity.Team;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import reactor.util.annotation.Nullable;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@NoArgsConstructor
public class TeamFeedAddForm {
    @NotBlank
    private String content;

    @Nullable
    private List<MultipartFile> imageFiles;
    @Nullable
    private List<MultipartFile> generalFiles;

    @Builder
    private TeamFeedAddForm(String content, List<MultipartFile> imageFiles, List<MultipartFile> generalFiles) {
        this.content = content;
        this.imageFiles = (imageFiles != null) ? imageFiles : new ArrayList<>();
        this.generalFiles = (generalFiles != null) ? generalFiles : new ArrayList<>();
    }

    public TeamFeedDto createTeamFeedDto(Team team) {
        Map<FileType, List<MultipartFile>> teamFeedFiles = getFileTypeListMap();
        return TeamFeedDto.builder()
                .team(team)
                .content(content)
                .teamFeedFiles(teamFeedFiles)
                .build();
    }

    private Map<FileType, List<MultipartFile>> getFileTypeListMap() {
        Map<FileType, List<MultipartFile>> userFeedFiles = new ConcurrentHashMap<>();
        userFeedFiles.put(FileType.IMAGE, imageFiles);
        userFeedFiles.put(FileType.GENERAL, generalFiles);
        return userFeedFiles;
    }
}
