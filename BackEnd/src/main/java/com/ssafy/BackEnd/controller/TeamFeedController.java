package com.ssafy.BackEnd.controller;

import com.ssafy.BackEnd.dto.TeamFeedAddForm;
import com.ssafy.BackEnd.dto.TeamFeedDto;
import com.ssafy.BackEnd.dto.UserFeedDto;
import com.ssafy.BackEnd.entity.*;
import com.ssafy.BackEnd.exception.CustomException;
import com.ssafy.BackEnd.exception.ErrorCode;
import com.ssafy.BackEnd.repository.ProfileRepository;
import com.ssafy.BackEnd.repository.TeamFeedRepository;
import com.ssafy.BackEnd.service.FileStore;
import com.ssafy.BackEnd.service.TeamFeedFileServiceImpl;
import com.ssafy.BackEnd.service.TeamFeedService;
import com.ssafy.BackEnd.service.TeamService;
import com.ssafy.BackEnd.util.UserFeedAddForm;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;


@RestController
@RequiredArgsConstructor
@RequestMapping("/teamfeed")
@Slf4j
public class TeamFeedController {
    private static final Logger logger = LogManager.getLogger(TeamFeedController.class);

    private final TeamFeedRepository teamFeedRepository;
    private final TeamFeedService teamFeedService;
    private final TeamFeedFileServiceImpl teamFeedFileService;
    private final ProfileRepository profileRepository;
    private final TeamService teamService;
    private final FileStore fileStore;


    @ApiOperation(value = "팀 피드 생성")
    @PostMapping("/{team_id}/{profile_id}")
    public ResponseEntity<TeamFeed> createTeamFeed(@PathVariable Long team_id, @PathVariable Long profile_id, @ModelAttribute TeamFeedAddForm teamFeedAddForm, BindingResult bindingResult) throws IOException, NotFoundException {
        if (bindingResult.hasErrors()) {
            log.info("bindingResult : {}", bindingResult.getFieldError());
            return new ResponseEntity<TeamFeed>((TeamFeed) null, HttpStatus.NOT_FOUND);
        }

        Team team = teamService.findByTeam(team_id);
        if (team == null) {
            logger.error("NO TEAM DATA");
            throw new CustomException("no team", ErrorCode.NO_DATA_ERROR);
        }

        TeamFeedDto teamFeedDto = teamFeedAddForm.createTeamFeedDto(team);
        TeamFeed teamFeed = teamFeedService.createTeamFeed(profile_id, teamFeedDto);
        logger.info("INFO SUCCESS");
        return new ResponseEntity<TeamFeed>(teamFeed, HttpStatus.OK);
    }


    @ApiOperation(value = "전체 팀 피드 조회")
    @GetMapping
    public ResponseEntity<List<TeamFeed>> findAllTeamFeed() {
        List<TeamFeed> teamfeeds = teamFeedService.showFindTeamFeedList();
        if (teamfeeds.isEmpty()) {
            logger.info("NULL TEADFEEDS");
        }

        logger.info("INFO SUCCESS");
        return new ResponseEntity<List<TeamFeed>>(teamfeeds, HttpStatus.OK);
    }

    @GetMapping("/ourteamfeed/{team_id}")
    public ResponseEntity<List<TeamFeed>> findOurTeamFeed(@PathVariable long team_id) {
        List<TeamFeed> our_teamfeeds = teamFeedService.showOurTeamFeedList(team_id);
        if (our_teamfeeds.isEmpty()) {
            logger.info("NULL TEAMFEEDS");
        }

        return new ResponseEntity<List<TeamFeed>>(our_teamfeeds, HttpStatus.OK);
    }

    @PutMapping("/{teamfeed_id}/{profile_id}")
    @ApiOperation(value = "팀 피드 수정")
    public ResponseEntity<TeamFeed> modifyTeamFeed(@PathVariable Long teamfeed_id, @PathVariable Long profile_id, @ModelAttribute TeamFeedAddForm teamFeedAddForm, BindingResult bindingResult) throws IOException {
        //        TeamFeed teamFeed = teamFeedDto.createTeamFeed(teamFeedDto);
        if (bindingResult.hasErrors()) {
            log.info("bindingResult : {}", bindingResult.getFieldError());
            return new ResponseEntity<TeamFeed>((TeamFeed) null, HttpStatus.NOT_FOUND);
        }
        TeamFeed teamFeed = teamFeedRepository.findByFeedId(teamfeed_id);
        if (teamFeed == null) {
            throw new CustomException("no teamfeed info", ErrorCode.NO_DATA_ERROR);
        }

        TeamFeedDto teamFeedDto = teamFeedAddForm.createTeamFeedDto(teamFeed.getTeam());
        TeamFeed newTeamFeed = teamFeedService.modifyTeamFeed(teamFeed, profile_id, teamFeedDto);

        logger.info("INFO SUCCESS");
        return new ResponseEntity<TeamFeed>(teamFeed, HttpStatus.OK);
    }

    @DeleteMapping("/{teamfeed_id}/{profile_id}")
    @ApiOperation(value = "팀 피드 삭제")
    public ResponseEntity<Void>deleteTeamFeed(@PathVariable Long teamfeed_id, @PathVariable Long profile_id) {
        teamFeedService.deleteTeamFeed(teamfeed_id, profile_id);

        logger.info("INFO SUCCESS");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public UrlResource processImg(@PathVariable String filename) throws MalformedURLException {

        logger.info("INFO SUCCESS");
        return new UrlResource("file:" + fileStore.createPath(filename, FileType.IMAGE));
    }

    @GetMapping("/files/{filename}")
    public ResponseEntity<UrlResource> processFiles(@PathVariable String filename, @RequestParam String originName) throws MalformedURLException {
        UrlResource urlResource = new UrlResource("file:" + fileStore.createPath(filename, FileType.GENERAL));
        String encodedUploadFileName = UriUtils.encode(originName, StandardCharsets.UTF_8);
        String contentDisposition = "files; filename=\"" + encodedUploadFileName + "\"";

        logger.info("INFO SUCCESS");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }

    @DeleteMapping("/files/{filename}")
    public void deleteFiles(@PathVariable String filename) {
        teamFeedFileService.deleteFile(filename);
        logger.info("INFO SUCCESS");
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> download(@PathVariable String filename) throws IOException {
        Path path = Paths.get("/home/ubuntu/files/generals/" + filename);
        String contentType = Files.probeContentType(path);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename(filename, StandardCharsets.UTF_8)
                .build());

        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
