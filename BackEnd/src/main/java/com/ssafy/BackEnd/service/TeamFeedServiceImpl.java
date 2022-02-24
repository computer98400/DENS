package com.ssafy.BackEnd.service;

import com.ssafy.BackEnd.dto.TeamFeedDto;
import com.ssafy.BackEnd.entity.*;
import com.ssafy.BackEnd.exception.CustomException;
import com.ssafy.BackEnd.exception.ErrorCode;
import com.ssafy.BackEnd.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamFeedServiceImpl implements TeamFeedService{

    private final TeamFeedRepository teamFeedRepository;

    private final TeamFeedFileService teamFeedFileService;

    private final TeamKeywordRepository teamKeywordRepository;

    private final TeamFeedKeywordRepository teamFeedKeywordRepository;

    private final ProfileRepository profileRepository;

    private HashTagAlgorithm hashTagAlgorithm = new HashTagAlgorithm();

    private final TeamMemberRepository teamMemberRepository;

    private final UserRepository userRepository;

    private final TeamFeedFileRepository teamFeedFileRepository;



    @Override
    public TeamFeed createTeamFeed(Long profile_id, TeamFeedDto teamFeedDto) throws IOException {
        TeamFeed teamFeed = teamFeedDto.createTeamFeed(teamFeedDto);
        User user = userRepository.findByProfileId(profile_id);
        List<TeamMember> teamMembers = teamFeed.getTeam().getTeam_member();
        boolean flag = false;

        for (TeamMember teamMember : teamMembers) {
            if (teamMember.getUser().equals(user)) {
                flag = true;
                break;
            }
        }
        if (flag) {
            System.out.println("들어왔다");
            List<TeamFeedFile> teamFeedFiles = teamFeedFileService.saveTeamFeedFiles(teamFeedDto.getTeamFeedFiles());

            for (TeamFeedFile teamFeedFile : teamFeedFiles) {
                log.info(teamFeedFile.getOriginalFileName());
                teamFeedFile.setTeam_feed(teamFeed);
                teamFeedFileRepository.save(teamFeedFile);
            }

            List<String> keywords = hashTagAlgorithm.strList(teamFeed.getContent()); //새로운 팀피드 키워드
            List<TeamFeedKeyword> teamFeedKeywords = new ArrayList<>();

            for (String keyword : keywords) {
                if (teamKeywordRepository.findTeamKeyword(keyword, teamFeed.getTeam()) == null) {
                    System.out.println("새로운 키워드");
                    TeamKeyword newTeamKeyword = new TeamKeyword();
                    newTeamKeyword.setName(keyword);
                    newTeamKeyword.setCount(1);
                    newTeamKeyword.setTeam(teamFeed.getTeam());
                    teamKeywordRepository.save(newTeamKeyword);

                    TeamFeedKeyword newTeamFeedKeyword = TeamFeedKeyword.builder().teamFeed(teamFeed).name(keyword).build();
                    teamFeedKeywordRepository.save(newTeamFeedKeyword);
                    teamFeedKeywords.add(newTeamFeedKeyword);
                } else {
                    TeamKeyword findTeamKeyword = teamKeywordRepository.findTeamKeyword(keyword, teamFeed.getTeam());
                    System.out.println(findTeamKeyword.getName() + " 같은키워드");
                    System.out.println(findTeamKeyword.getCount() + "기존 수");
                    findTeamKeyword.setCount(findTeamKeyword.getCount() + 1);
                    System.out.println(findTeamKeyword.getCount() + "새로운수");
                    TeamFeedKeyword findTeamFeedKeyword = teamFeedKeywordRepository.findTeamFeedKeyword(keyword, teamFeed.getTeamfeed_id());
                    if (findTeamFeedKeyword == null) {
                        TeamFeedKeyword newTeamFeedKeyword = TeamFeedKeyword.builder().teamFeed(teamFeed).name(keyword).build();
                        teamFeedKeywords.add(newTeamFeedKeyword);
                    } else {
                        teamFeedKeywords.add(findTeamFeedKeyword);
                    }
                }
            }
            teamFeed.setTeamFeedKeywords(teamFeedKeywords);
            //teamFeed.setTeamFeedFiles(teamFeedFiles);
            teamFeed.setWriter(Long.toString(user.getProfile().getProfile_id())); //프로필아이디 로 변경
            return teamFeedRepository.save(teamFeed);
        } else {
            System.out.println("권한이 없습니다");
            throw new CustomException("권한 없음", ErrorCode.UNAUTH_USER_ERROR);
        }
    }


    @Override
    public TeamFeed modifyTeamFeed(TeamFeed teamFeed, Long profile_id, TeamFeedDto teamFeedDto) throws IOException {
//        Profile profile = profileRepository.findById(profile_id).get();
//
//        if(!teamFeed.getWriter().equals(profile_id)) { //피드작성자와 프로필작성자 프로필 아이디가 같지 않다면
//            System.out.println("권한이 없습니다");
//            throw new CustomException("권한 없음", ErrorCode.UNAUTH_USER_ERROR);
//        }

        List<TeamFeedFile> teamFeedFiles = teamFeedFileService.saveTeamFeedFiles(teamFeedDto.getTeamFeedFiles());

        for (TeamFeedFile teamFeedFile : teamFeedFiles) {
            log.info(teamFeedFile.getOriginalFileName());
            teamFeedFile.setTeam_feed(teamFeed);
            teamFeedFileRepository.save(teamFeedFile);
        }

        teamFeed.setContent(teamFeedDto.getContent());

        List<TeamFeedKeyword> teamFeedKeywords = teamFeed.getTeamFeedKeywords();
        List<TeamKeyword> teamKeywords = teamFeed.getTeam().getTeam_keyword();
        List<String> keywords = hashTagAlgorithm.strList(teamFeed.getContent());
        List<TeamKeyword> deleteKeywords = new ArrayList<>();
        for (TeamKeyword teamKeyword : teamKeywords) {
            if (!keywords.contains(teamKeyword.getName())) {
                System.out.println(teamKeyword.getName());
                System.out.println("==============if");
                teamKeyword.setCount(teamKeyword.getCount() - 1);
                deleteKeywords.add(teamKeyword);
////                keywords.remove(teamFeedKeyword.getKeyword().getName());
//                teamFeedKeywords.remove(teamFeedKeyword);
            } else if (keywords.contains(teamKeyword.getName())) {
                System.out.println(teamKeyword.getName());
                System.out.println("=============else");
                keywords.remove(teamKeyword.getName());
            }
        }
        for (TeamKeyword deleteKeyword : deleteKeywords) {
//            teamFeedKeywords.remove(deleteKeyword);
            if (deleteKeyword.getCount() <= 0) {

//                deleteKeyword.setTeam_feed(null);
//                deleteKeyword.setKeyword(null);
//                teamFeedKeywordRepository.deleteById(deleteKeyword.getTeamfeedkeyword_id());
                System.out.println("=======remove");
                teamFeedKeywords.remove(deleteKeyword);
                System.out.println("==========delete");
                teamKeywordRepository.delete(deleteKeyword);
//                teamFeedKeywordRepository.deleteById(deleteKeyword.getTeamfeedkeyword_id());
//                keywordRepository.deleteById(deleteKeyword.getKeyword().getKeyword_id());

//                teamFeedKeywords.remove(deleteKeyword);
            }
        }

        for (String key : keywords) System.out.println(key.getBytes(StandardCharsets.UTF_8));
        for (String keyword : keywords) {
            if (teamKeywordRepository.findTeamKeyword(keyword, teamFeed.getTeam()) == null) {
                TeamKeyword newTeamKeyword = TeamKeyword.builder().team(teamFeed.getTeam()).name(keyword).count(1).build();
                teamKeywordRepository.save(newTeamKeyword);
                TeamFeedKeyword newTeamFeedKeyword = TeamFeedKeyword.builder().teamFeed(teamFeed).name(keyword).build();
                teamFeedKeywordRepository.save(newTeamFeedKeyword);
                teamFeedKeywords.add(newTeamFeedKeyword);
            } else {
                TeamKeyword findTeamKeyword = teamKeywordRepository.findTeamKeyword(keyword, teamFeed.getTeam());
                findTeamKeyword.setCount(findTeamKeyword.getCount()+1);

                if (teamFeedKeywordRepository.findTeamFeedKeyword(keyword, teamFeed.getTeamfeed_id()) == null) {
                    TeamFeedKeyword newTeamFeedKeyword = TeamFeedKeyword.builder().teamFeed(teamFeed).name(keyword).build();
                    teamFeedKeywordRepository.save(newTeamFeedKeyword);
                }
            }
            }
            teamFeed.setTeamFeedKeywords(teamFeedKeywords);

//            List<TeamFeedFile> teamFeedFiles = teamFeed.getTeamFeedFiles();
//
//
//            Map<FileType, List<MultipartFile>> teamFeedDtoFiles = teamFeedDto.getTeamFeedFiles();
//            List<MultipartFile> imageFiles = teamFeedDtoFiles.get(FileType.IMAGE);
//            List<MultipartFile> generalFiles = teamFeedDtoFiles.get(FileType.GENERAL);
//
//            List<MultipartFile> addImageFiles = new ArrayList<>();
//            List<MultipartFile> addGeneralFiles = new ArrayList<>();
//
//            Map<FileType, List<MultipartFile>> addFiles = new HashMap<>();
//
//            for (MultipartFile image: imageFiles) {
//                if (!teamFeedFiles.contains(image)) {
//                    addImageFiles.add(image);
//                }
//            }
//
//            addFiles.put(FileType.IMAGE, addImageFiles);
//
//
//            for (MultipartFile file: generalFiles) {
//                if (!teamFeedFiles.contains(file)) {
//                    addGeneralFiles.add(file);
//                }
//            }
//
//            addFiles.put(FileType.GENERAL, addGeneralFiles);
//
//            ;

            teamFeedRepository.save(teamFeed);
            return teamFeed;

    }

    // 수정 teamFeed만 삭제되도록
    @Override
    public void deleteTeamFeed(long teamfeed_id, long profile_id) {
        User user = userRepository.findByProfileId(profile_id);
        TeamFeed teamFeed = teamFeedRepository.findByFeedId(teamfeed_id);
        List<TeamMember> teamMembers = teamFeed.getTeam().getTeam_member();
        TeamMember teamLeader = new TeamMember();
        for (TeamMember teamMember : teamMembers) {
            if (teamMember.getTeam_identity().equals(TeamMemberIdentity.LEADER)) {
                teamLeader = teamMember;
                break;
            }
        }

//        if (!teamFeed.getWriter().equals(profile_id) && !teamFeed.getWriter().equals(teamLeader.getUser().getName())) {
//            System.out.println("권한이 없습니다");
//            throw new CustomException("권한 없음", ErrorCode.UNAUTH_USER_ERROR);
//        }

//        Team team = teamFeed.getTeam();
        List<TeamFeedKeyword> teamFeedKeywordList = teamFeed.getTeamFeedKeywords();
        List<TeamKeyword> deleteKeywordList = new ArrayList<>();
        for (TeamFeedKeyword teamFeedKeyword : teamFeedKeywordList) {
//            teamFeedKeyword.getKeyword().setCount(teamFeedKeyword.getKeyword().getCount() - 1);
//            teamFeedKeyword.getKeyword().setTeam_feed_keyword(null);
            TeamKeyword teamKeyword = teamKeywordRepository.findTeamKeyword(teamFeedKeyword.getName(), teamFeed.getTeam());
            teamKeyword.setCount(teamKeyword.getCount()-1);
            if (teamKeyword.getCount() <= 0) {
                teamKeywordRepository.delete(teamKeyword);
            }
            teamFeedKeywordRepository.delete(teamFeedKeyword);
            System.out.println("=======================");
//            teamFeedKeywords.remove(teamFeedKeyword);
//            System.out.println(teamFeedKeyword.getKeyword().getName());
        }

        teamFeedRepository.delete(teamFeed);
        System.out.println("=====remove");
//        System.out.println(teamFeed.getContent());
////        teamFeedRepository.delete(teamFeed);
//        System.out.println(teamfeed_id);
//        System.out.println(teamFeed.getTeamfeed_id());
//
//        teamFeed.setTeamFeedKeywords(null);
//        teamFeedRepository.deleteById(teamfeed_id);
    }


    @Override
    public List<TeamFeed> showFindTeamFeedList() {
        List<TeamFeed> teamFeeds = new ArrayList<>();
        teamFeedRepository.findAll().forEach(teamFeed -> teamFeeds.add(teamFeed));

        return teamFeeds;
    }

    @Override
    public List<TeamFeed> showOurTeamFeedList(long team_id) {
        List<TeamFeed> teamFeeds = new ArrayList<>();
        teamFeeds = teamFeedRepository.findByTeam_Team_id(team_id);

        return teamFeeds;
    }


}
