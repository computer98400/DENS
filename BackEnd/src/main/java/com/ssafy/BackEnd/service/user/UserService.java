package com.ssafy.BackEnd.service.user;

import com.ssafy.BackEnd.entity.User;
import com.ssafy.BackEnd.entity.UserIdentity;
import com.ssafy.BackEnd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    List<User> showUserList();
    User findByEmail(String email);
    UserIdentity findUserAuth(String email);

}
