package com.ssafy.BackEnd.service;

import com.ssafy.BackEnd.entity.FileType;
import com.ssafy.BackEnd.entity.TeamFeedFile;
import com.ssafy.BackEnd.entity.UserFeedFile;
import com.ssafy.BackEnd.repository.TeamFeedFileRepository;
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
public class TeamFeedFileServiceImpl implements TeamFeedFileService {

    private final TeamFeedFileRepository teamFeedFileRepository;
    private final FileStore fileStore;

    @Override
    public List<TeamFeedFile> saveTeamFeedFiles(Map<FileType, List<MultipartFile>> multipartFileListMap) throws IOException {
        List<TeamFeedFile> imageFiles = fileStore.storeTeamFeedFiles(multipartFileListMap.get(FileType.IMAGE), FileType.IMAGE);
        List<TeamFeedFile> generalFiles = fileStore.storeTeamFeedFiles(multipartFileListMap.get(FileType.GENERAL), FileType.GENERAL);
        List<TeamFeedFile> result = Stream.of(imageFiles, generalFiles)
                .flatMap(f -> f.stream())
                .collect(Collectors.toList());
        return result;
    }

    @Override
    public Map<FileType, List<TeamFeedFile>> findTeamFeedFiles() {
        List<TeamFeedFile> teamFeedFiles = teamFeedFileRepository.findAll();
        Map<FileType, List<TeamFeedFile>> result = teamFeedFiles.stream()
                .collect(Collectors.groupingBy(TeamFeedFile::getFileType));
        return result;
    }

    @Override
    public void save(TeamFeedFile file) {
        teamFeedFileRepository.save(file);
    }

    @Override
    public void deleteFile(String filename) {
        TeamFeedFile file = teamFeedFileRepository.findByFileName(filename);
        file.setTeam_feed(null);
        teamFeedFileRepository.delete(file);
    }
}
