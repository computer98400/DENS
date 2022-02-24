package com.ssafy.BackEnd.repository;


import com.ssafy.BackEnd.entity.ProfileKeyword;
import com.ssafy.BackEnd.entity.Team;
import com.ssafy.BackEnd.entity.TeamFeedKeyword;
import com.ssafy.BackEnd.entity.TeamKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamKeywordRepository extends JpaRepository<TeamKeyword, Long> {

    @Query(value = "select k from TeamKeyword k where name = :name and team = :team")
    TeamKeyword findTeamKeyword(String name, Team team);

    @Query(value = "select k from TeamKeyword k where name = :name")
    TeamKeyword findByName(String name);

    List<TeamKeyword> findByNameContaining(String keyword);
}
