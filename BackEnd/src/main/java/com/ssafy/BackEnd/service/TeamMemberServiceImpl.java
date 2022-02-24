package com.ssafy.BackEnd.service;

import com.ssafy.BackEnd.dto.TeamMemberDto;
import com.ssafy.BackEnd.dto.UserDto;
import com.ssafy.BackEnd.entity.Team;
import com.ssafy.BackEnd.entity.TeamMember;
import com.ssafy.BackEnd.entity.TeamMemberIdentity;
import com.ssafy.BackEnd.entity.User;
import com.ssafy.BackEnd.repository.TeamMemberRepository;
import com.ssafy.BackEnd.repository.TeamRepository;
import com.ssafy.BackEnd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TeamMemberServiceImpl implements TeamMemberService {

    private final UserRepository userRepository;

    private final TeamRepository teamRespository;

    private final TeamMemberRepository teamMemberRepository;

    @Override
    public TeamMember addTeamMember(long team_id, long profile_id) { //팀에 팀원 추가하는 기능
        User user = userRepository.findByProfileId(profile_id); //해당 유저정보 가져오기
        System.out.println(user.getEmail() + "유저 이르");
//        for(User u : user){
//            System.out.println(u.getEmail()+" hihi");
//        }
        //System.out.println(user.getEmail()+" "+teamName+" hihi");
        Team team = teamRespository.findByTeam(team_id);
        System.out.println(team.getTitle() +"팀이름");
        List<TeamMember> findTeamMember = team.getTeam_member();
        for (TeamMember member : findTeamMember) {
            System.out.println(member.getUser().getEmail() + "이메일");
            if (member.getUser().getProfile().getProfile_id() == profile_id) {
                return null;
            }
        }

        System.out.println(user.getEmail() + "유저 이르");
        TeamMember teamMember = TeamMember.builder().team(team).user(user).teamMemberIdentity(TeamMemberIdentity.MEMBER).build();
        teamMemberRepository.save(teamMember);

        return teamMember;
    }

    @Override
    @Transactional
    public TeamMember deleteTeamMember(long team_id, long profile_id) {
        Team findTeam = teamRespository.findByTeam(team_id);

        List<TeamMember> findTeamMember = findTeam.getTeam_member();
        System.out.println(findTeam.getTitle());
        for (TeamMember member : findTeamMember) {
            System.out.println("팀 프로필" + member.getUser().getProfile().getProfile_id());
            if (member.getUser().getProfile().getProfile_id() == profile_id) {
                System.out.println(member.getUser().getProfile().getProfile_id() + "해당 팀멤버 프로필");
                System.out.println(profile_id + "보내보는 프로필 아이디");
                teamMemberRepository.delete(member);
                findTeamMember.remove(member);
                return member;
            }
        }
        return null;
    }

    @Override
    public Team mergeTeam(Long teamId1, Long teamId2) {
        Team team1 = teamRespository.findById(teamId1).get();
        System.out.println("team 1 : "+team1.getTitle());
        Team team2 = teamRespository.findById(teamId2).get();
        System.out.println("team 2 : "+team2.getTitle());
        List<TeamMember> teamMembers2 = team2.getTeam_member();
        for (TeamMember member : teamMembers2) {
            System.out.println("member email : "+member.getUser().getEmail());
            member.setTeam(team1);
            member.setTeam_identity(TeamMemberIdentity.MEMBER);
//            if (member.getTeam_identity().equals(TeamMemberIdentity.LEADER)) {
//                member.setTeam_identity(TeamMemberIdentity.MEMBER);
//                addTeamMember(member.getUser().getEmail(), team1.getTitle());
//            } else {
//                addTeamMember(member.getUser().getEmail(), team1.getTitle());
//            }
        }
        teamRespository.save(team1);
        teamRespository.delete(team2);
//        teamRespository.deleteById(team2.getTeam_id());
        return team1;

    }

    @Override
    public TeamMember addTeamLeader(String email, Team team) { //팀에 팀원 추가하는 기능
        User user = userRepository.findByEmail(email); //해당 유저정보 가져오기
//        for(User u : user){
//            System.out.println(u.getEmail()+" hihi");
//        }
        //System.out.println(user.getEmail()+" "+teamName+" hihi");
        TeamMember teamMember = TeamMember.builder().team(team).user(user).teamMemberIdentity(TeamMemberIdentity.LEADER).build();

        teamMemberRepository.save(teamMember);

        return teamMember;
    }

    @Override
    public List<User> showTeamMemberList(Long team_id) {
        Team team = teamRespository.findByTeam(team_id);
        System.out.println(team.getTitle());
        List<TeamMember> teamMembers = team.getTeam_member();
        List<User> teammembers_infos = new ArrayList<>();
        //Map<String, TeamMemberIdentity> teamMemberList = new HashMap<>();

        for (TeamMember teamMember: teamMembers) {
            System.out.println(teamMember.getUser().getName());
            teammembers_infos.add(teamMember.getUser());

            System.out.println("팀권한" + teamMember.getTeam_identity());

            //teamMemberList.put(teamMember.getUser().getName(), teamMember.getTeam_identity());
        }


        return teammembers_infos;
    }

//    @Override
//    public TeamMember getMyTeamIndentity(long team_id, long profile_id) {
//        //System.out.println(team_id + "FFF" + email);
//
//        TeamMember teamMember = teamMemberRepository.findByIdentity(team_id, profile_id);
//        System.out.println(teamMember.getTeam_identity() + "왜안나와");
//
//        return teamMember;
//    }


}



