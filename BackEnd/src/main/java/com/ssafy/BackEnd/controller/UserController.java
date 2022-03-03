package com.ssafy.BackEnd.controller;

import com.ssafy.BackEnd.entity.Response;
import com.ssafy.BackEnd.entity.User;
import com.ssafy.BackEnd.repository.UserRepository;
import com.ssafy.BackEnd.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "유저 컨트롤러")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping
    @ApiOperation(value = "유저 리스트 출력")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.showUserList();

        System.out.println("유저 리스트 출력~");
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


}
