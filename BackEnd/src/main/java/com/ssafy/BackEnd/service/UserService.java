package com.ssafy.BackEnd.service;

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

    //private final UserRepository userRepository;

//    public String SignUp(User user){
//
//        userRepository.save(user);
//        return user.getEmail();
//    }
//
//    public User SignIn(String email, String password) throws Exception{
//        User user = userRepository.findUser(email, password);
//        if(user == null) throw new Exception("사용자가 없음");
//        if(!user.getPassword().equals(password)) throw new Exception("비밀번호 X");
//        return user;
////        //Optional<User> byId = userRepository.findById(email); // user정보를 가져오는거? email,
////        System.out.println("b : "+email+" "+password);
////        User user = userRepository.findUser(email, password);
////        //System.out.println(" D "+ user.getEmail()+" "+user.getPassword());
////        if(user != null) return true;
////        else return false;
//    }
//
////    public User loadUserByEmail(String email){
////        User user = userRepository.findByEmail(email);
////
////    }
}
