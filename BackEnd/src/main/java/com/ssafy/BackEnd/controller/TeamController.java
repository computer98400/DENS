package com.ssafy.BackEnd.controller;

import com.ssafy.BackEnd.dto.TeamDto;
import com.ssafy.BackEnd.entity.*;
import com.ssafy.BackEnd.exception.CustomException;
import com.ssafy.BackEnd.exception.ErrorCode;
import com.ssafy.BackEnd.repository.*;
import com.ssafy.BackEnd.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import com.ssafy.BackEnd.service.TeamService;
import com.ssafy.BackEnd.repository.TeamRepository;
import com.ssafy.BackEnd.entity.Team;


@RestController
@Api(tags = "팀 컨트롤러 API")
@RequestMapping("/team")
@Log4j2
public class TeamController {
    private static final Logger logger = LogManager.getLogger(TeamController.class);

    @Autowired
    TeamService teamService;

    @Autowired
    TeamRepository teamRespository;

    @Autowired
    ProfileService profileService;

    @Autowired
    TeamMemberService teamMemberService;

    @Autowired
    TeamKeywordRepository teamKeywordRepository;

    private HashTagAlgorithm hashTagAlgorithm = new HashTagAlgorithm();
    //@ExceptionHandler({NotFoundException.class, NullPointerException.class})
    @GetMapping
    @ApiOperation(value = "팀 목록 가져오기")
    public ResponseEntity<List<Team>> getAllTeams() throws NotFoundException {
        List<Team> teams = teamService.showTeamList();

        if (teams == null) {
            logger.error("NO ALL TEAL LIST");
            System.out.println("전체 팀 목록이 없습니다");
            return new ResponseEntity<List<Team>>(teams, HttpStatus.OK);
            //throw new CustomException(ErrorCode.NO_DATA_ERROR);
        }
        logger.info("INFO SUCCESS");

        return new ResponseEntity<List<Team>>(teams, HttpStatus.OK);
    }

    @GetMapping("/myteam/{profile_id}") //프로필아이디로 내 팀 목록 가져오기
    @ApiOperation(value = "내 팀 목록 가져오기")
    public ResponseEntity<List<Team>> getMyTeams(@PathVariable Long profile_id) throws NotFoundException {
        List<Team> my_teams = teamService.showMyTeamList(profile_id);

        if (my_teams.isEmpty()) {
            logger.error("NO MY TEAM");
            System.out.println("내 팀 목록이 없습니다");
            //throw new CustomException(ErrorCode.NO_DATA_ERROR);
        }
        logger.info("INFO SUCCESS");

        return new ResponseEntity<List<Team>>(my_teams, HttpStatus.OK);
    }

    @ExceptionHandler({NotFoundException.class, NullPointerException.class})
    @PostMapping(value="/create/{profileId}")
    @ApiOperation(value = "팀 만들기")
    public ResponseEntity<Team> createTeam(@RequestBody TeamDto teamDto, @PathVariable Long profileId) throws NotFoundException {
        Team team = teamDto.createTeam();
        if (team == null) {
            logger.error("NO TEAM INFO");
            throw new CustomException(ErrorCode.NO_DATA_ERROR);
        }
        Team newTeam = teamService.createTeam(team);
        Profile findProfile = profileService.findById(profileId).get();

        if (findProfile == null) {
            logger.error("NO PROFILE");
            throw new CustomException(ErrorCode.NO_DATA_ERROR);
        }
        TeamMember teamMember = teamMemberService.addTeamLeader(findProfile.getEmail(), newTeam);
        logger.info("INFO SUCCESS");

        return new ResponseEntity<Team>(newTeam, HttpStatus.OK);

    }

    @GetMapping("/showteam/{team_id}")
    @ApiOperation(value = "팀 조회")
    public ResponseEntity<Map<Object, Object>> findTeam(@PathVariable Long team_id) throws NotFoundException {
        //Team team = teamDto.createTeam();

        Team team = teamService.findByTeam(team_id);

        List<TeamMember> teamMembers = team.getTeam_member();
        Map<Object, Object> result = new HashMap<>();
        result.put(team.getTeam_id(), teamMembers);
        System.out.println(team);
        if(team == null) {
             logger.error("NO TEAM INFO");
             throw new CustomException(ErrorCode.NO_DATA_ERROR);
             //return new ResponseEntity<Team>(team, HttpStatus.OK);
         }

        System.out.println("team id : "+team.getTeam_id());
        logger.info("INFO SUCCESS");

        return new ResponseEntity<Map<Object, Object>>(result, HttpStatus.OK);

    }

    //@ExceptionHandler({NotFoundException.class, NullPointerException.class})
    @PutMapping("/{profile_id}/{team_id}")
    @ApiOperation(value = "팀 수정") //팀 수정이 무엇에 대한 수정인가(name과 content에 대한 수정??)
    public ResponseEntity<Team> modifyTeam(@PathVariable long profile_id, @PathVariable long team_id, @RequestBody TeamDto teamDto) throws NotFoundException {
        Team findTeam = teamService.findByTeam(team_id);
        if (findTeam == null) {
            logger.error("NO MODIFY TEAM");
            throw new CustomException(ErrorCode.NO_DATA_ERROR);
        }
        Team team = teamService.modifyTeam(profile_id, findTeam, teamDto);
        logger.info("INFO SUCCESS");

        return new ResponseEntity<Team>(team, HttpStatus.OK);
    }

    //@ExceptionHandler({NotFoundException.class, NullPointerException.class})
    @DeleteMapping("/{team_id}")
    @ApiOperation(value = "팀 삭제")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long team_id) {
        teamService.deleteTeam(team_id);
        logger.info("INFO SUCCESS");

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/profile/{team_id}/{profile_id}")
    @ApiOperation(value = "팀 프로필 수정")
    public ResponseEntity<Team> modifyTeamProfile(@RequestBody TeamDto teamDto, @PathVariable long team_id, @PathVariable long profile_id) throws NotFoundException {
        Team team = teamService.findByTeam(team_id);
        if (team == null) {
            logger.error("NO TEAM PROFILE INFO");
            throw new CustomException(ErrorCode.NO_DATA_ERROR);
        }
        //System.out.println("팀제목" + team.getContent());
        Team newTeam = teamService.modifyTeamProfile(teamDto, team, profile_id);
        logger.info("INFO SUCCESS");

        return new ResponseEntity<Team>(newTeam, HttpStatus.OK);
    }

    @GetMapping("/leaderteam/{profile_id}")
    @ApiOperation(value = "내가 팀장인 팀들 정보 반환")
    public ResponseEntity<List<Team>> getLeaderTeam(@PathVariable long profile_id) throws NotFoundException {
        List<Team> teams = teamService.showLeaderTeams(profile_id);
        if (teams.isEmpty()) {
            logger.info("내가 팀장인 팀이 없습니다");
        }

        return new ResponseEntity<List<Team>>(teams, HttpStatus.OK);
    }

}