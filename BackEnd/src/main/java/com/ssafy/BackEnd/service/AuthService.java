package com.ssafy.BackEnd.service;

import com.ssafy.BackEnd.dto.UserDto;
import com.ssafy.BackEnd.entity.Profile;
import com.ssafy.BackEnd.entity.User;
import com.ssafy.BackEnd.entity.UserIdentity;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import java.util.Map;

public interface AuthService {

    final String REDIS_CHANGE_PASSWORD_PREFIX="CPW";

    void signUp(UserDto user);

    boolean validateDuplicateUser(User user);

    ResponseEntity<Profile> createProfile(User user);

    User signIn(String email, String password) throws Exception;

    ResponseEntity<User> verifyEmail(String key) throws NotFoundException;

    String sendVerificationMail(User user) throws NotFoundException, MessagingException;

    void modifyUserRole(User user, UserIdentity role);

    User findByEmail(String email) throws NotFoundException;

    User findByName(String name) throws NotFoundException;

    void requestChangePassword(User user) throws NotFoundException, MessagingException;

    User changePassword(User user, String password) throws NotFoundException;

    boolean isPasswordUuidValidate(String key);
}
