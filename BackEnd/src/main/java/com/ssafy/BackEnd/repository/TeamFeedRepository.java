package com.ssafy.BackEnd.repository;

import com.ssafy.BackEnd.entity.TeamFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamFeedRepository extends JpaRepository<TeamFeed, Long> {
    @Query("select t from TeamFeed t where teamfeed_id = :teamfeed_id")
    TeamFeed findByFeedId(long teamfeed_id);

    @Override
    void deleteById(Long aLong);

    @Override
    void delete(TeamFeed entity);

    @Query("select t from TeamFeed t where team_id = :team_id")
    List<TeamFeed> findByTeam_Team_id(long team_id);
}
