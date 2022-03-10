package com.ssafy.BackEnd.service.team;

import com.ssafy.BackEnd.dto.TeamDto;
import com.ssafy.BackEnd.entity.*;
import com.ssafy.BackEnd.repository.*;
import com.ssafy.BackEnd.service.etc.HashTagAlgorithm;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    private final TeamMemberRepository teamMemberRepository;

    private final UserRepository userRepository;

    private final ProfileRepository profileRepository;

    private final TeamKeywordRepository teamKeywordRepository;


    private HashTagAlgorithm hashTagAlgorithm = new HashTagAlgorithm();

    @Override
    public Team findByTeam(Long team_id) throws NotFoundException {
        Team findTeam = teamRepository.findByTeam(team_id);
        return findTeam;
    }

    @Override
    public Team createTeam(Team team) {
        List<String> keywords = hashTagAlgorithm.strList(team.getContent());

        List<TeamKeyword> teamKeywordList = new ArrayList<>();
        for (String keyword : keywords) {
            if (teamKeywordRepository.findByName(keyword) == null) {
                TeamKeyword newTeamKeyword = TeamKeyword.builder().name(keyword).count(1).team(team).build();
                teamKeywordRepository.save(newTeamKeyword);
                teamKeywordList.add(newTeamKeyword);
            } else {
                TeamKeyword findTeamKeyword = teamKeywordRepository.findTeamKeyword(keyword, team);
                findTeamKeyword.setCount(findTeamKeyword.getCount() + 1);

                if (findTeamKeyword == null) {
                    TeamKeyword newTeamKeyword = TeamKeyword.builder().name(keyword).count(1).team(team).build();
                    teamKeywordRepository.save(newTeamKeyword);
                    teamKeywordList.add(newTeamKeyword);
                }
            }
        }
        team.setTeam_keyword(teamKeywordList);
        teamRepository.save(team);
        return team;
    }

    @Override
    public Team modifyTeam(Long profile_id, Team team, TeamDto teamDto) {
        team.setTitle(teamDto.getTitle());
        teamRepository.save(team);
        return team;

    }

    @Override
    public void deleteTeam(long team_id) {
        Team team = teamRepository.findByTeam(team_id);
        teamRepository.delete(team);
    }

    @Override
    public List<Team> showFindTeamList(String keyword) {

        List<Team> teams = teamRepository.findByTitleContaining(keyword);
        System.out.println(teams.toString());
        return teams;
    }

    @Override
    public List<Team> showTeamList() {
        List<Team> teams = new ArrayList<>();
        teamRepository.findAll().forEach(team -> teams.add(team));

        return teams;
    }

    @Override
    public List<Team> showMyTeamList(Long profile_id) {
        List<Team> my_teams = new ArrayList<>();
        teamRepository.showMyTeamList(profile_id).forEach(myteam -> my_teams.add(myteam));

        System.out.println(my_teams.size());

        for (Team team : my_teams) {
          System.out.println("팀제목" + team.getTitle());
        }

        return my_teams;

    }

    @Override
    public Team modifyTeamProfile(TeamDto teamDto, Team team, long profile_id) {

        Profile profile = profileRepository.findById(profile_id).get();
        User user = userRepository.findByEmail(profile.getEmail());
        List<TeamMember> teamMembers = team.getTeam_member();
        for (TeamMember teamMember : teamMembers) {
            if (teamMember.getTeam_identity().equals(TeamMemberIdentity.LEADER) && teamMember.getUser().equals(user)) {

                team.setContent(teamDto.getContent());
                teamRepository.save(team);

                List<TeamKeyword> teamKeywords = team.getTeam_keyword(); //기존 팀소개 키워드
                for (TeamKeyword teamKeyword : teamKeywords) {
                    System.out.println("기존" + teamKeyword.getName());
                }
                List<String> keywords = hashTagAlgorithm.strList(team.getContent()); //새로운 팀소개 키워드 추출
                for (String keyword : keywords) {
                    System.out.println("새로운" + keyword);
                }
                List<TeamKeyword> deleteKeywords = new ArrayList<>();
                for (TeamKeyword teamKeyword : teamKeywords) {
                    if (keywords.contains(teamKeyword.getName()) == false) { //기존 키워드 안 가지고 있으면 수 감소
                        System.out.println(teamKeyword.getName());
                        System.out.println("==============if");
                        teamKeyword.setCount(teamKeyword.getCount() - 1);
                        deleteKeywords.add(teamKeyword);

                    } else if (keywords.contains(teamKeyword.getName())) { //기존키워드에 새로운 키워드가 있으면
                        System.out.println(teamKeyword.getName());
                        System.out.println("=============else");
                        keywords.remove(teamKeyword.getName());
                    }
                }
                for (TeamKeyword deleteKeyword : deleteKeywords) {
                    if (deleteKeyword.getCount() <= 0) {
                        System.out.println("=======remove");
                        teamKeywords.remove(deleteKeyword);
                        System.out.println("==========delete");
                        teamKeywordRepository.delete(deleteKeyword);
                    }
                }
                for (String key : keywords) System.out.println(key.getBytes(StandardCharsets.UTF_8));
                for (String keyword : keywords) {
                    if (teamKeywordRepository.findTeamKeyword(keyword, team) == null) {
                        TeamKeyword newTeamKeyword = TeamKeyword.builder().name(keyword).count(1).team(team).build();
                        teamKeywordRepository.save(newTeamKeyword);
                        teamKeywords.add(newTeamKeyword);
                    } else {
                        TeamKeyword teamKeyword = teamKeywordRepository.findTeamKeyword(keyword, team);
                        teamKeyword.setCount(teamKeyword.getCount()+1);
                    }
                    team.setTeam_keyword(teamKeywords);
                }
            }
            else {
                System.out.println("권한이 없습니다.");
            }
        }
        return team;
    }

    @Override
    public TeamMember setTeamLeader(Team team, String email) {
        User user = userRepository.findByEmail(email);
        TeamMember teamMember = TeamMember.builder().team(team).user(user).teamMemberIdentity(TeamMemberIdentity.LEADER).build();

        teamMemberRepository.save(teamMember);

        return teamMember;
    }

    @Override
    public List<Team> findTeamByKeyword(String keyword) {
        List<TeamKeyword> findTeamKeywords = teamKeywordRepository.findByNameContaining(keyword);
        List<Team> findTeams = new ArrayList<>();
        for (TeamKeyword teamKeyword : findTeamKeywords) {
            System.out.println(teamKeyword.getName());
            if (!findTeams.contains(teamKeyword.getTeam())) {
                findTeams.add(teamKeyword.getTeam());
            }
        }
        return findTeams;
    }

    @Override
    public List<Team> showLeaderTeams(long profile_id) {
        List<Team> teams = showMyTeamList(profile_id);
        List<Team> leader_teams = new ArrayList<>();

        for (Team team : teams) {
            if (team.getTeam_member().get(0).getUser().getProfile().getProfile_id() == profile_id) {
                leader_teams.add(team);
            }
        }
        return leader_teams;
    }
}

