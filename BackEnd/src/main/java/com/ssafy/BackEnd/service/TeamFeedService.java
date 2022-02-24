package com.ssafy.BackEnd.service;

import com.ssafy.BackEnd.dto.TeamFeedDto;
import com.ssafy.BackEnd.entity.Team;
import com.ssafy.BackEnd.entity.TeamFeed;

import java.io.IOException;
import java.util.List;

public interface TeamFeedService {
    TeamFeed createTeamFeed(Long profile_id, TeamFeedDto teamFeeddto) throws IOException;

    TeamFeed modifyTeamFeed(TeamFeed teamFeed, Long profile_id, TeamFeedDto teamFeedDto) throws IOException;

    void deleteTeamFeed(long teamfeed_id, long profile_id);

    List<TeamFeed> showFindTeamFeedList();

    List<TeamFeed> showOurTeamFeedList(long team_id);

}
