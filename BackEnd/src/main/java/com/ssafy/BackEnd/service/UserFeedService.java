package com.ssafy.BackEnd.service;

import com.ssafy.BackEnd.dto.UserFeedDto;
import com.ssafy.BackEnd.entity.UserFeed;

import java.io.IOException;
import java.util.List;

public interface UserFeedService {
    UserFeed createUserFeed(UserFeed userFeed);

    UserFeed modifyUserFeed(long userfeed_id, UserFeed userFeed);

    void deleteUserFeed(long userfeed_id);

    List<UserFeed> showFindUserFeedList();

    List<String> checkHashTag(String content);

    UserFeed post(UserFeedDto userFeedDto) throws IOException;
}
