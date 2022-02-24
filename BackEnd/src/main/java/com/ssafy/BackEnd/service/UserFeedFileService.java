package com.ssafy.BackEnd.service;

import com.ssafy.BackEnd.entity.FileType;
import com.ssafy.BackEnd.entity.UserFeedFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserFeedFileService {

    List<UserFeedFile> saveUserFeedFiles(Map<FileType, List<MultipartFile>> multipartFileListMap) throws IOException;

    Map<FileType, List<UserFeedFile>> findUserFeedFiles();

    void save(UserFeedFile file);
}
