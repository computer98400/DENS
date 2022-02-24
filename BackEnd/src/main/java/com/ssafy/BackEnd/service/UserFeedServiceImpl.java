package com.ssafy.BackEnd.service;

import com.ssafy.BackEnd.dto.UserFeedDto;
import com.ssafy.BackEnd.entity.User;
import com.ssafy.BackEnd.entity.UserFeed;
import com.ssafy.BackEnd.entity.UserFeedFile;
import com.ssafy.BackEnd.repository.UserFeedFileRepository;
import com.ssafy.BackEnd.repository.UserFeedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserFeedServiceImpl implements UserFeedService{

    private final UserFeedRepository userFeedRepository;

    private final UserFeedFileService userFeedFileService;

    private HashTagAlgorithm hashTagAlgorithm = new HashTagAlgorithm();

    @Override
    public UserFeed createUserFeed(UserFeed userFeed) {
        userFeedRepository.save(userFeed);
        // 키워드 알고리즘 넣기
        List<String> hashtag = hashTagAlgorithm.strList(userFeed.getContent());
        //checkHashTag(userFeed.getContent()); // 일단 엔티티에서

        return userFeed;
    }

    @Override
    public UserFeed modifyUserFeed(long userfeed_id, UserFeed userFeed) {
        UserFeed old_userfeed = userFeedRepository.findByFeedId(userfeed_id);
        old_userfeed.setContent(userFeed.getContent());

        userFeedRepository.save(userFeed);

        return userFeed;
    }

    @Override
    public void deleteUserFeed(long userfeed_id) {
        userFeedRepository.deleteById(userfeed_id);
    }

    @Override
    public List<UserFeed> showFindUserFeedList() {
        List<UserFeed> userFeeds = new ArrayList<>();
        userFeedRepository.findAll().forEach(userfeed -> userFeeds.add(userfeed));

        // 조회되지 않는 경우 처리하기
        return userFeeds;
    }

    @Override
    public List<String> checkHashTag(String content) { // 키워드 분리 알고리즘
        String exStr = "안녕하세요 #프론트엔드 입니다 #백엔드";


        return null;
    }

    @Override
    public UserFeed post(UserFeedDto userFeedDto) throws IOException {
        List<UserFeedFile> userFeedFiles = userFeedFileService.saveUserFeedFiles(userFeedDto.getUserFeedFiles());
        for(UserFeedFile userFeedFile : userFeedFiles) {
            log.info(userFeedFile.getOriginalFileName());
        }
        UserFeed userFeed = userFeedDto.createUserFeed();
        userFeed.setUserFeedFiles(userFeedFiles);
        return userFeedRepository.save(userFeed);
    }
}
