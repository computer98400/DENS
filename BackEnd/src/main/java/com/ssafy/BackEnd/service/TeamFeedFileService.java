package com.ssafy.BackEnd.service;

import com.ssafy.BackEnd.entity.FileType;
import com.ssafy.BackEnd.entity.TeamFeedFile;
import com.ssafy.BackEnd.entity.UserFeedFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface TeamFeedFileService {

    List<TeamFeedFile> saveTeamFeedFiles(Map<FileType, List<MultipartFile>> multipartFileListMap) throws IOException;

    Map<FileType, List<TeamFeedFile>> findTeamFeedFiles();

    void save(TeamFeedFile file);

    void deleteFile(String filename);
}
