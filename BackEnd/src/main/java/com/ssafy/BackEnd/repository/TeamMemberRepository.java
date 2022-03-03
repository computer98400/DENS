package com.ssafy.BackEnd.repository;

import com.ssafy.BackEnd.entity.TeamMember;
import com.ssafy.BackEnd.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    TeamMember findByUser(User user);

    @Query("select t from TeamMember t where team_id = :team_id")
    List<TeamMember> showTeamMemberList(Long team_id);

    @Query("select m from TeamMember m where email = :email")
    List<TeamMember> findByEmail(String email);

}
