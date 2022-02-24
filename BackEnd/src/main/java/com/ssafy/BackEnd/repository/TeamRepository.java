package com.ssafy.BackEnd.repository;

import java.util.List;

import com.ssafy.BackEnd.entity.Team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeamRepository extends JpaRepository<Team, Long> {
    //Optional<Team> findByTeam(Long team_id);

    @Query("select t from Team t where team_id = :team_id")
    Team findByTeam(Long team_id);

    List<Team> findByTitleContaining(String keyword);


    Team findByTitle(String title);


    @Query(value = "select distinct team.team_id, team.title, team.content, team.created_date from team inner join teammember on team.team_id = teammember.team_id "
            + "inner join user on teammember.email = user.email and user.profile_id = :profile_id ", nativeQuery = true)
    List<Team> showMyTeamList(@Param("profile_id") Long profile_id);

    @Override
    void deleteById(Long aLong);

}
