package com.ssafy.BackEnd.service;

import com.ssafy.BackEnd.entity.User;
import com.ssafy.BackEnd.entity.UserIdentity;
import com.ssafy.BackEnd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> showUserList() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> users.add(user));

        return users;
    }

    @Override
    public User findByEmail(String email) {
        User findUser = userRepository.findByEmail(email);
        return findUser;
    }

    @Override
    public UserIdentity findUserAuth(String email) {
        User user = findByEmail(email);
        UserIdentity userAuth = user.getIdentity();
        return userAuth;
    }
}
