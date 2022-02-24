package com.ssafy.BackEnd.service;

import com.ssafy.BackEnd.dto.UserDto;
import com.ssafy.BackEnd.entity.Profile;
//import com.ssafy.BackEnd.entity.Salt;
import com.ssafy.BackEnd.entity.User;
import com.ssafy.BackEnd.entity.UserIdentity;
import com.ssafy.BackEnd.repository.ProfileRepository;
import com.ssafy.BackEnd.repository.UserRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{


    private final EmailService emailService;

    private final RedisUtil redisUtil;

    private final UserRepository userRepository;

    private final ProfileRepository profileRepository;

    private final SaltUtil saltUtil;
    private String key;

    @Override
    @Transactional
    public void signUp(UserDto user) {
        String password = user.getPassword();
        String encPass = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println("auth pwd : "+user.getPassword());
        User save = User.builder()
                .email(user.getEmail())
                .name(user.getName())
                .password(encPass)
                .createDate(LocalDateTime.now())
                .identity(UserIdentity.ROLE_UNAUTH)
                .build();
        userRepository.save(save);
        System.out.println("save : "+save.getEmail());
        System.out.println("save : "+save.getPassword());
        System.out.println("save : "+save.getCreateDate());
        System.out.println("save : "+save.getIdentity());
        System.out.println("save : "+save.getName());

    }

    @Override
    public boolean validateDuplicateUser(User user) {
        User findUsers = userRepository.findByEmail(user.getEmail());
        if (findUsers != null) {
            return false;
        }
        return true;
    }

    @Override
    public ResponseEntity<Profile> createProfile(User user) {
        Profile profile = Profile.builder()
                .email(user.getEmail())
                .name(user.getName())
                .git(null)
                .git_id(null)
                .position(null)
                .stack(null)
                .image(null)
                .createDate(LocalDateTime.now())
                .build();
        profileRepository.save(profile);
        user.setProfile(profile);
        userRepository.save(user);

        return new ResponseEntity<Profile>(profile, HttpStatus.OK);
    }

    @Override
    public User signIn(String email, String password) throws Exception{
        User findUser = userRepository.findByEmail(email);
        System.out.println("find "+findUser.getEmail());
        if(findUser == null) throw new Exception ("멤버가 조회되지 않습니다.");
        boolean checkpw = BCrypt.checkpw(password, findUser.getPassword());
        if (checkpw == false) throw new Exception("비밀번호가 틀립니다.");
        return findUser;
    }

    @Override
    public User findByEmail(String email) throws NotFoundException {
        User findUser = userRepository.findByEmail(email);
        if(findUser == null) throw new NotFoundException("멤버가 조회되지 않습니다.");
        return findUser;
    }

    @Override
    public User findByName(String name) throws NotFoundException {
        User user = userRepository.findByName(name);
        if(user == null) throw new NotFoundException("멤버가 조회되지 않습니다.");
        return user;
    }

    @Override
    public void requestChangePassword(User user) throws NotFoundException, MessagingException {
        String CHANGE_PASSWORD_LINK = "http://i6c201.p.ssafy.io:3040/user/password/";
        if(user == null) throw new NotFoundException("멤버가 조회되지 않습니다.");
        String key = REDIS_CHANGE_PASSWORD_PREFIX+ UUID.randomUUID();
        redisUtil.setDataExpire(key, user.getName(), 60*30L);
        emailService.sendMail(user.getEmail(), "사용자 비밀번호 안내 메일", CHANGE_PASSWORD_LINK+key);
    }

    @Override
    public boolean isPasswordUuidValidate(String key) {
        String userId = redisUtil.getData(key);
        return !userId.equals("");
    }

    @Override
    public User changePassword(User user, String password) throws NotFoundException {
        if(user==null) throw new NotFoundException("changePassword(), 멤버가 조회되지 않습니다.");
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public String sendVerificationMail(User user) throws NotFoundException, MessagingException {
        String VERIFICATION_LINK = "http://i6c201.p.ssafy.io:3040/certi";
        if(user==null) throw new NotFoundException("멤버가 조회되지 않음");
        UUID uuid = UUID.randomUUID();
        System.out.println("key : " + uuid);
        redisUtil.setDataExpire(uuid.toString(),user.getEmail(), 60 * 30L);
        String htmlStr = "<h2>안녕하세요 DeNS입니다.</h2><br>"
                +"<h3>"+user.getName() + "님</h3>" + "<p>인증하기 버튼을 누르시면 로그인을 하실 수 있습니다. <br>"
                +"<a href="+VERIFICATION_LINK+">인증하기</a></p>";
        emailService.sendMail(user.getEmail(),"[DeNS] 회원가입 인증메일입니다.", htmlStr);
//        Map<String, Object> result = new HashMap<>();
//        result.put(", uuid.toString());
        return uuid.toString();
    }

    public ResponseEntity<User> verifyEmail(@PathVariable String key) throws NotFoundException {
        String userId = redisUtil.getData(key);
        User user = userRepository.findByEmail(userId);
        if(user==null) throw new NotFoundException("멤버가 조회되지않음");
        modifyUserRole(user, UserIdentity.ROLE_USER); //enum으로 수정
        redisUtil.deleteData(key);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    public void modifyUserRole(User user, UserIdentity role){
        user.setIdentity(role);
        userRepository.save(user);
    }
}
