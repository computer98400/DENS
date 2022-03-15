package com.ssafy.BackEnd.controller;

import com.ssafy.BackEnd.entity.Profile;
import com.ssafy.BackEnd.entity.Team;
import com.ssafy.BackEnd.exception.CustomException;
import com.ssafy.BackEnd.exception.ErrorCode;
import com.ssafy.BackEnd.service.profile.ProfileService;
import com.ssafy.BackEnd.service.team.TeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
@Api(tags = "검색 컨트롤러 API")
public class SearchController {
    private static final Logger logger = LogManager.getLogger(SearchController.class);

    private final ProfileService profileService;
    private final TeamService teamService;

    @GetMapping("/user")
    @ApiOperation(value = "유저이름으로 유저 검색")
    public ResponseEntity<List<Profile>> findSearchedUsers(String keyword) throws NotFoundException {
        HttpStatus status;
        List<Profile> userList = profileService.showFindUserList(keyword);
        System.out.println("전달 받은 값 : " + keyword);

        status = HttpStatus.OK;
        System.out.println("status : "+status);
        return new ResponseEntity<>(userList, status);
    }

    @GetMapping("/user/{profile_id}")
    @ApiOperation(value = "프로필 아이디로 프로필 반환")
    public ResponseEntity<Profile> findSearchOneProfile(@PathVariable Long profile_id) throws NotFoundException {
        Profile profile = profileService.findById(profile_id).get();
        if(profile == null)
        {
            System.out.println("error");
            throw new CustomException("no searched profile", ErrorCode.NO_DATA_ERROR);

        }
        logger.info("INFO SUCCESS");
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @GetMapping("/team/{team_id}")
    @ApiOperation(value = "팀 아이디로 팀 반환")
    public ResponseEntity<Team> findSearchOneTeam(@PathVariable Long team_id) throws NotFoundException {
        Team team = teamService.findByTeam(team_id);
        if(team == null){
            System.out.println("team error");
            throw new CustomException("no searched team", ErrorCode.NO_DATA_ERROR);
        }
        logger.info("INFO SUCCESS");
        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    @GetMapping("/team")
    @ApiOperation(value = "팀 이름으로 팀 검색")
    public ResponseEntity<List<Team>> findSearchedTeams(@RequestParam String keyword) throws NotFoundException{
        HttpStatus status;
        List<Team> teamList = teamService.showFindTeamList(keyword);
        System.out.println("keyword : "+keyword);


        status = HttpStatus.OK;
        return new ResponseEntity<>(teamList, status);
    }

    @GetMapping("/keyword/user")
    @ApiOperation(value = "키워드로 유저 검색")
    public ResponseEntity<List<Profile>> findSearchedUserByKeyword(@RequestParam String keyword) {
        List<Profile> searchedUsers = profileService.findUserByKeyword(keyword);
        if (searchedUsers.isEmpty()) {
            System.out.println("유저없음");
            logger.info("SEARCHED NULL");
            return new ResponseEntity<List<Profile>>((List<Profile>) null, HttpStatus.NOT_FOUND);
        }
        logger.info("INFO SUCCESS");
        return new ResponseEntity<List<Profile>>(searchedUsers, HttpStatus.OK);
    }

    @GetMapping("/keyword/team")
    @ApiOperation(value = "키워드로 팀 검색")
    public ResponseEntity<List<Team>> findSearchedTeamByKeyword(@RequestParam String keyword) {
        List<Team> searchedTeams = teamService.findTeamByKeyword(keyword);
        if (searchedTeams == null) {
            logger.info("SEARCHED NULL");
            return new ResponseEntity<List<Team>>((List<Team>) null, HttpStatus.NOT_FOUND);
        }
        logger.info("INFO SUCCESS");
        return new ResponseEntity<List<Team>>(searchedTeams, HttpStatus.OK);
    }
}
