package com.ssafy.BackEnd.service;


import com.ssafy.BackEnd.entity.*;
import com.ssafy.BackEnd.entity.Request.RequestModifyProfile1;
import com.ssafy.BackEnd.entity.Request.RequestModifyProfile2;
import com.ssafy.BackEnd.repository.ProfileKeywordRepository;
import com.ssafy.BackEnd.repository.ProfileRepository;
import com.ssafy.BackEnd.repository.TeamMemberRepository;
import com.ssafy.BackEnd.repository.UserRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private SaltUtil saltUtil;

    private final ProfileKeywordRepository profileKeywordRepository;

    private final TeamMemberRepository teamMemberRepository;

    @Override
    public Optional<Profile> findProfile(Long user_id) throws NotFoundException{
        User findUser = userRepository.findById(user_id).get();
        if (findUser == null) throw new NotFoundException("회원이 조회되지 않습니다.");
        Optional<Profile> findProfile = profileRepository.findByName(findUser.getName());
        return findProfile;
    }

    @Override
    public Optional<Profile> findById(Long profile_id) throws NotFoundException {
        Optional<Profile> findProfile = profileRepository.findById(profile_id);
        if (findProfile == null) throw new NotFoundException("회원이 조회되지 않습니다.");
        return findProfile;
    }

    @Override
    public Profile findbyEmail(String email) throws NotFoundException {
        Profile profile = findbyEmail(email);
        if (profile == null) throw new NotFoundException("프로필이 조회되지 않습니다.");
        return profile;
    }

    @Override
    public User findUserById(Long profile_id) {
        Profile profile = profileRepository.findById(profile_id).get();
        User user = userRepository.findByEmail(profile.getEmail());
        return user;
    }

    // 이름, 이메일 수정 x, user 번호로 조회하기
    @Override
    public Profile modifyProfile(Profile findProfile, RequestModifyProfile2 requestModifyProfile2) throws NotFoundException {

        User user = userRepository.findByEmail(findProfile.getEmail());
        System.out.println("position : "+requestModifyProfile2.getPosition());
        System.out.println("stack : "+requestModifyProfile2.getStack());
        findProfile.setPosition(requestModifyProfile2.getPosition());
        findProfile.setStack(requestModifyProfile2.getStack());
        findProfile.setGit(requestModifyProfile2.getGit());
        findProfile.setGit_id(requestModifyProfile2.getGit_id());

        profileRepository.save(findProfile);
        userRepository.save(user);

        return findProfile;
    }

    @Override
    public List<Profile> showFindUserList(String keyword){
        List<Profile> profiles = profileRepository.findByNameContaining(keyword);
        return profiles;
    }

    @Override
    public void deleteUser(Long profile_id) {
        Profile findProfile = profileRepository.findById(profile_id).get();
        User user = userRepository.findByEmail(findProfile.getEmail());
        userRepository.delete(user);
        profileRepository.delete(findProfile);
    }

    @Override
    public List<String> addKeyword(Profile profile, String content) {
        List<String> strlist = new ArrayList<>();

        String[] strArr = content.split(" ");
        for (String s : strArr){
            if(s.charAt(0) == '#') {
                String key = s.substring(1);
                strlist.add(key);
            }
        }
        for (String word : strlist) {
            System.out.println("word : "+word);
        }

        return strlist;
    }

    @Override
    public List<Profile> findUserByKeyword(String keyword) {
        List<ProfileKeyword> keywords = profileKeywordRepository.findByNameContaining(keyword);
        System.out.println(keywords.size());
        List<Profile> searchedUsers = new ArrayList<>();


        for (ProfileKeyword profileKeyword : keywords) {
            System.out.println(profileKeyword.getName().equals(keyword));
            if (!searchedUsers.contains(profileKeyword.getName())) { //검색어와 유저의 키워드가 같다면
                searchedUsers.add(profileKeyword.getProfile());
                System.out.println("이름" + profileKeyword.getName());
            }
        }

//        for (int i = 0; i < keywords.size(); i++) {
//            System.out.println("왜 안 돌아가");
//            if (keywords.get(i).getName().equals(keyword)) { //검색어와 유저의 키워드가 같다면
//                searchedUsers.add(keywords.get(i).getProfile());
//                System.out.println("이름" + keywords.get(i).getName());
//            }
//        }

        return searchedUsers;
    }

    @Override
    public List<ProfileKeyword> getProfileKeywords(Long profile_id) {
        Profile profile = profileRepository.findById(profile_id).get();
        List<ProfileKeyword> profileKeywordList = profile.getProfile_keyword();
        return profileKeywordList;
    }

    @Override
    public List<TeamKeyword> getTeamKeywords(Long profile_id) {
        Profile profile = profileRepository.findById(profile_id).get();
        List<TeamMember> teamMembers = teamMemberRepository.findByEmail(profile.getEmail());
        List<TeamKeyword> teamKeywordList = new ArrayList<>();
        for (TeamMember teamMember : teamMembers) {
            List<TeamKeyword> teamKeywords = teamMember.getTeam().getTeam_keyword();
            for (TeamKeyword teamKeyword : teamKeywords) {
                if (!teamKeywordList.contains(teamKeyword)) {
                    teamKeywordList.add(teamKeyword);
                }

            }
        }
        return teamKeywordList;
    }

}
