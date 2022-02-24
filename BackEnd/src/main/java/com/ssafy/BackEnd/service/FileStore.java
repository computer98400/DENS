package com.ssafy.BackEnd.service;

import com.ssafy.BackEnd.entity.FileType;
import com.ssafy.BackEnd.entity.TeamFeedFile;
import com.ssafy.BackEnd.entity.UserFeed;
import com.ssafy.BackEnd.entity.UserFeedFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}/")
    private String fileDirPath;

    public List<UserFeedFile> storeUserFeedFiles(List<MultipartFile> multipartFiles, FileType fileType) throws IOException {
        List<UserFeedFile> userFeedFiles = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                userFeedFiles.add(storeUserFeedFile(multipartFile, fileType));
            }
        }
        return userFeedFiles;
    }

    public List<TeamFeedFile> storeTeamFeedFiles(List<MultipartFile> multipartFiles, FileType fileType) throws IOException {
        List<TeamFeedFile> teamFeedFiles = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                teamFeedFiles.add(storeTeamFeedFile(multipartFile, fileType));
            }
        }
        return teamFeedFiles;
    }

    public UserFeedFile storeUserFeedFile(MultipartFile multipartFile, FileType fileType) throws IOException {
        if(multipartFile.isEmpty()) {
            return null;
        }

        String originalFileName = multipartFile.getOriginalFilename();
        String fileName = createFilename(originalFileName);
        multipartFile.transferTo(new File(createPath(fileName, fileType)));

        return UserFeedFile.builder()
                .originalFileName(originalFileName)
                .storePath(fileName)
                .fileType(fileType)
                .build();
    }

    public TeamFeedFile storeTeamFeedFile(MultipartFile multipartFile, FileType fileType) throws IOException {
        if(multipartFile.isEmpty()) {
            return null;
        }

        String originalFileName = multipartFile.getOriginalFilename();
        String fileName = createFilename(originalFileName);
        multipartFile.transferTo(new File(createPath(fileName, fileType)));

        return TeamFeedFile.builder()
                .originalFileName(originalFileName)
                .storePath(fileName)
                .fileType(fileType)
                .build();
    }

    public String createPath(String fileName, FileType fileType) {
        String viaPath = (fileType == FileType.IMAGE) ? "images/" : "generals/";
        return fileDirPath + viaPath + fileName;
    }

    public String createFilename(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        String fileName = uuid + ext;

        return fileName;
    }

    private String extractExt(String originalFileName) {
        int idx = originalFileName.lastIndexOf(".");
        String ext = originalFileName.substring(idx);
        return ext;
    }
}
