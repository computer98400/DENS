package com.ssafy.BackEnd.repository;

import com.ssafy.BackEnd.entity.TeamMember;
import com.ssafy.BackEnd.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    TeamMember findByUser(User user);

    @Query("select t from TeamMember t where team_id = :team_id")
    List<TeamMember> showTeamMemberList(Long team_id);

    @Query("select m from TeamMember m where email = :email")
    List<TeamMember> findByEmail(String email);

    @Query("select m from TeamMember m where team_id = :team_id and email = :email")
    TeamMember findTeamLeader2(Long team_id, String email);

//    @Query(value = "select teammember.teammember_id, teammember.team_identity, teammember.team_id, teammember.email from teammember inner join user on teammember.email = user.email "
//    + "inner join profile on user.profile_id = :profile_id", nativeQuery = true)
//    TeamMember findByIdentity(long team_id, @Param("profile_id") long profile_id);
}
