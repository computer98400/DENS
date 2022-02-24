package com.ssafy.BackEnd.service;

import com.ssafy.BackEnd.entity.FileType;
import com.ssafy.BackEnd.entity.UserFeedFile;
import com.ssafy.BackEnd.repository.UserFeedFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserFeedFileServiceImpl implements UserFeedFileService {

    private final UserFeedFileRepository userFeedFileRepository;
    private final FileStore fileStore;

    @Override
    public List<UserFeedFile> saveUserFeedFiles(Map<FileType, List<MultipartFile>> multipartFileListMap) throws IOException {
        List<UserFeedFile> imageFiles = fileStore.storeUserFeedFiles(multipartFileListMap.get(FileType.IMAGE), FileType.IMAGE);
        List<UserFeedFile> generalFiles = fileStore.storeUserFeedFiles(multipartFileListMap.get(FileType.GENERAL), FileType.GENERAL);
        List<UserFeedFile> result = Stream.of(imageFiles, generalFiles)
                .flatMap(f -> f.stream())
                .collect(Collectors.toList());
        return result;
    }

    @Override
    public Map<FileType, List<UserFeedFile>> findUserFeedFiles() {
        List<UserFeedFile> userFeedFiles = userFeedFileRepository.findAll();
        Map<FileType, List<UserFeedFile>> result = userFeedFiles.stream()
                .collect(Collectors.groupingBy(UserFeedFile::getFileType));
        return result;
    }

    @Override
    public void save(UserFeedFile file) {
        userFeedFileRepository.save(file);
    }
}
