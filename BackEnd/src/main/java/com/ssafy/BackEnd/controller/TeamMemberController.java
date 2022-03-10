package com.ssafy.BackEnd.controller;

import com.ssafy.BackEnd.entity.Team;
import com.ssafy.BackEnd.entity.TeamMember;
import com.ssafy.BackEnd.entity.User;
import com.ssafy.BackEnd.exception.CustomException;
import com.ssafy.BackEnd.exception.ErrorCode;
import com.ssafy.BackEnd.service.team.TeamMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/teammember")
@RequiredArgsConstructor
@Api(tags = "팀 멤버 컨트롤러")
public class TeamMemberController {
    private static final Logger logger = LogManager.getLogger(TeamMemberController.class);

    private final TeamMemberService teamMemberService;

    @PostMapping("/{team_id}/{profile_id}")
    @ApiOperation(value = "팀 멤버 추가하기")
    public ResponseEntity<TeamMember> createTeamMember(@PathVariable long team_id, @PathVariable long profile_id) {

        TeamMember teamMember = teamMemberService.addTeamMember(team_id, profile_id);
        if (teamMember == null) {
            throw new CustomException(ErrorCode.NOT_ADD_TEAMMEMBER);
        }

        logger.info("INFO SUCCESS");
        return new ResponseEntity<TeamMember>(teamMember, HttpStatus.OK);
    }

    @DeleteMapping("/{team_id}/{profile_id}")
    @ApiOperation(value = "팀 멤버 방출하기")
    public ResponseEntity<TeamMember> deleteTeamMember(@PathVariable long team_id, @PathVariable long profile_id) {
        TeamMember teammember = teamMemberService.deleteTeamMember(team_id, profile_id);

        if (teammember == null) {
            logger.error("NO DELETE TEAMMEMBER");
            throw new CustomException(ErrorCode.NO_DATA_ERROR);
            //return new ResponseEntity<TeamMember>((TeamMember) null, HttpStatus.NOT_FOUND);
        }
        logger.info("INFO SUCCESS");

        return new ResponseEntity<TeamMember>(teammember, HttpStatus.OK);

    }

    @PutMapping
    @ApiOperation(value = "팀 병합하기")
    public ResponseEntity<Team> mergeTeam(@RequestParam Long teamId1, @RequestParam Long teamId2) {
        Team newTeam = teamMemberService.mergeTeam(teamId1, teamId2);
        if (newTeam == null) {
            logger.error("NO MERGE TEAM");
            throw new CustomException(ErrorCode.NO_DATA_ERROR);
        }

        logger.info("INFO SUCCESS");
        return new ResponseEntity<Team>(newTeam, HttpStatus.OK);
    }

    @GetMapping("/{team_id}") //팀 아이디로 팀별 멤버 목록 반환
    @ApiOperation(value = "팀 검색하기")
    public ResponseEntity<List<User>> showTeamMemberList(@PathVariable Long team_id) {
        List<User> teammemberlist = new ArrayList<>();
        teammemberlist = teamMemberService.showTeamMemberList(team_id);

        if (teammemberlist.isEmpty()) {
            logger.error("NO TEAMMEMBER");
            throw new CustomException(ErrorCode.NO_DATA_ERROR);
        }
        logger.info("INFO SUCCESS");
        return new ResponseEntity<List<User>>(teammemberlist, HttpStatus.OK);
    }
}
