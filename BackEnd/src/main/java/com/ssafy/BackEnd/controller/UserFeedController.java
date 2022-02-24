package com.ssafy.BackEnd.controller;

import com.ssafy.BackEnd.dto.UserFeedDto;
import com.ssafy.BackEnd.entity.FileType;
import com.ssafy.BackEnd.entity.Profile;
import com.ssafy.BackEnd.exception.CustomException;
import com.ssafy.BackEnd.exception.ErrorCode;
import com.ssafy.BackEnd.repository.UserFeedRepository;
import com.ssafy.BackEnd.repository.UserRepository;
import com.ssafy.BackEnd.service.FileStore;
import com.ssafy.BackEnd.service.ProfileService;
import com.ssafy.BackEnd.service.UserFeedService;
import com.ssafy.BackEnd.util.UserFeedAddForm;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ssafy.BackEnd.entity.UserFeed;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/userfeed")
@RequiredArgsConstructor
@Slf4j
public class UserFeedController {
    private static final Logger logger = LogManager.getLogger(UserFeedController.class);


    private final UserFeedRepository userFeedRepository;
    private final UserFeedService userFeedService;
    private final ProfileService profileService;
    private final FileStore fileStore;



    // Read
    @ApiOperation(value = "유저 피드 조회")
    @GetMapping
    public ResponseEntity<List<UserFeed>> findAllUserFeed() {
        List<UserFeed> userfeeds = userFeedService.showFindUserFeedList();
        if (userfeeds.isEmpty()) {
            logger.info("NULL USERFEEDS");
        }

        logger.info("INFO SUCCESS");
        return new ResponseEntity<List<UserFeed>>(userfeeds, HttpStatus.OK);
    }

    // Update
    @PutMapping("/{userfeed_id}")
    @ApiOperation(value = "유저 피드 수정")
    public ResponseEntity<UserFeed> modifyUserFeed(@RequestBody UserFeedDto userFeedDto, @PathVariable long userfeed_id) {
        UserFeed userFeed = userFeedDto.createUserFeed();
        if (userFeed == null) {
            logger.error("NO USERFEED INFO");
            throw new CustomException(ErrorCode.NO_DATA_ERROR);
        }

        userFeedService.modifyUserFeed(userFeed.getUserfeed_id(), userFeed);

        logger.info("INFO SUCCESS");
        return new ResponseEntity<UserFeed>(userFeed, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/{userfeed_id}")
    @ApiOperation(value = "유저 피드 삭제")
    public ResponseEntity<Void>deleteUserFeed(@RequestBody UserFeedDto userFeedDto, @PathVariable long userfeed_id) {
        UserFeed userFeed = userFeedDto.createUserFeed();
        if (userFeed == null) {
            logger.error("NO DELETE USERFEED");
            throw new CustomException(ErrorCode.NO_DATA_ERROR);
        }
        userFeedService.deleteUserFeed(userFeed.getUserfeed_id());

        logger.info("INFO SUCCESS");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/{profile_id}")
    public String createUserFeed(@PathVariable Long profile_id, @Validated @ModelAttribute UserFeedAddForm userFeedAddForm, BindingResult bindingResult) throws IOException, NotFoundException {
        if (bindingResult.hasErrors()) {
            log.info("bindingResult : {}", bindingResult.getFieldError());
            return "post";
        }
        Profile profile = profileService.findById(profile_id).get();
        if (profile == null) {
            logger.error("NO PROFILE DATA");
            throw new CustomException(ErrorCode.NO_DATA_ERROR);
        }
        System.out.println("profile email : "+profile.getEmail());
        UserFeedDto userFeedDto = userFeedAddForm.createUserFeedDto(profile);
        System.out.println("content : "+userFeedDto.getContent());
        UserFeed post = userFeedService.post(userFeedDto);
        System.out.println("po content : "+post.getContent());

        logger.info("INFO SUCCESS");
        return "redirect:/main/board" + post.getProfile();
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
}
