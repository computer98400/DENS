package com.ssafy.BackEnd.controller;

import com.ssafy.BackEnd.entity.Profile;
import com.ssafy.BackEnd.entity.Team;
import com.ssafy.BackEnd.entity.User;
import com.ssafy.BackEnd.exception.CustomException;
import com.ssafy.BackEnd.exception.ErrorCode;
import com.ssafy.BackEnd.service.ProfileService;
import com.ssafy.BackEnd.service.TeamService;
import com.ssafy.BackEnd.service.UserService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
    private static final Logger logger = LogManager.getLogger(SearchController.class);

    private final ProfileService profileService;
    private final TeamService teamService;

    @GetMapping("/user")
    public ResponseEntity<List<Profile>> findSearchedUsers(String keyword) throws NotFoundException{
        HttpStatus status;
        List<Profile> userList = profileService.showFindUserList(keyword);
        System.out.println("전달 받은 값 : "+keyword);
        if(userList != null) {
            status = HttpStatus.OK;
            System.out.println("success\n"+userList.get(0).getName());
            logger.info("INFO SUCCESS");
        } else {
            status = HttpStatus.NO_CONTENT;
            System.out.println("no searched userlist");
            logger.info("SEARCHED NULL");
            //throw new CustomException("no searched userlist", ErrorCode.NO_DATA_ERROR);
        }

        return new ResponseEntity<>(userList, status);
    }

    @GetMapping("/user/{profile_id}")
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
    public ResponseEntity<List<Team>> findSearchedTeams(@RequestParam String keyword) throws NotFoundException{
        HttpStatus status;
        List<Team> teamList = teamService.showFindTeamList(keyword);
        System.out.println("keyword : "+keyword);
        if(teamList != null) {
            status = HttpStatus.OK;
            System.out.println("success\n"+teamList.get(0).getTeam_id());
            logger.info("INFO SUCCESS");
        } else {
            status = HttpStatus.NO_CONTENT;
            System.out.println("no searched teams");
            logger.info("SEARCHED NULL");
            //throw new CustomException("no searched teams", ErrorCode.NO_DATA_ERROR);
        }

        return new ResponseEntity<>(teamList, status);
    }

    @GetMapping("/keyword/user")
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
