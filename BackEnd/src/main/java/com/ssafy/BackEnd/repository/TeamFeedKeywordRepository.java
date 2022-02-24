package com.ssafy.BackEnd.repository;

import com.ssafy.BackEnd.entity.ProfileKeyword;
import com.ssafy.BackEnd.entity.TeamFeedKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamFeedKeywordRepository extends JpaRepository<TeamFeedKeyword, Long> {

    @Query(value = "select k from TeamFeedKeyword k where name = :name")
    TeamFeedKeyword findByName(String name);

    @Query(value = "select k from TeamFeedKeyword k where name = :name and teamfeed_id = :teamfeed_id")
    TeamFeedKeyword findTeamFeedKeyword(String name, Long teamfeed_id);


}
