package com.ssafy.BackEnd.service.team;

import com.ssafy.BackEnd.dto.TeamDto;
import com.ssafy.BackEnd.entity.Team;
import com.ssafy.BackEnd.entity.TeamMember;
import javassist.NotFoundException;

import java.util.List;

public interface TeamService {

    Team findByTeam(Long team_id) throws NotFoundException;

    Team createTeam(Team team);

    Team modifyTeam(Long profile_id, Team team, TeamDto teamDto);

    void deleteTeam(long team_id);

    List<Team> showFindTeamList(String keyword);

    List<Team> showTeamList();

    List<Team> showMyTeamList(Long profile_id);

    Team modifyTeamProfile(TeamDto teamDto, Team team, long profile_id);

    TeamMember setTeamLeader(Team team, String email);

    List<Team> findTeamByKeyword(String keyword);

    List<Team> showLeaderTeams(long profile_id);

}
