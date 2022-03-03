package com.ssafy.BackEnd.controller;

import com.ssafy.BackEnd.dto.UserDto;
import com.ssafy.BackEnd.entity.*;
import com.ssafy.BackEnd.entity.Request.RequestChangePassword2;
import com.ssafy.BackEnd.entity.Request.RequestVerifyEmail;
import com.ssafy.BackEnd.exception.CustomException;
import com.ssafy.BackEnd.exception.ErrorCode;

import com.ssafy.BackEnd.service.auth.AuthService;
import com.ssafy.BackEnd.service.chat.RedisUtil;
import com.ssafy.BackEnd.service.cookie.CookieService;
import com.ssafy.BackEnd.service.jwt.JwtServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Api(tags = "유저 관련 메인 컨트롤러")
public class MainController {
    private static final Logger logger = LogManager.getLogger(MainController.class);

    private final JwtServiceImpl jwtService;
    private final CookieService cookieService;
    private final RedisUtil redisUtil;
    
    private final AuthService authService;


    @PostMapping("/signup")
    @ApiOperation(value = "회원가입", notes = "사용자의 정보를 입력 받고 'success'면 회원가입 or 'fail이면 에러메세지", response = String.class)
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody UserDto userDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        System.out.println("up : "+userDto.getEmail());
        try {
            authService.signUp(userDto);
            System.out.println("userpwd : "+userDto.getPassword());
            status = HttpStatus.ACCEPTED;
            resultMap.put("message", "success");
            logger.info("INFO SUCCESS");
        }
        catch(Exception e) {
            status = HttpStatus.ACCEPTED;
            throw new CustomException(ErrorCode.SIGNUP_ERROR);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


    @GetMapping("/password/{key}")
    @ApiOperation(value = "비밀번호 변경 인증 절차", response = String.class)
    public ResponseEntity<Response> isPasswordUUIDValidate(@PathVariable String key) {
        Response response = new Response();
        try {
            if (authService.isPasswordUuidValidate(key)) {
                response.setResponse("success");
                response.setMessage("정상적인 접근입니다.");
                response.setData(null);
                logger.info("INFO SUCCESS");
                return new ResponseEntity<Response>(response, HttpStatus.OK);
            } else {
                response.setResponse("error");
                response.setMessage("유효하지 않은 key값입니다.");
                response.setData(null);
                return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            response.setResponse("error");
            response.setMessage("유효하지 않은 key값입니다.");
            response.setData(null);
            throw new CustomException("invalid key", ErrorCode.PASSWORD_VERIFY_ERROR);
        }
    }



    @PostMapping("/password")
    @ApiOperation(value = "사용자 비밀번호 변경요청", response = String.class)
    public ResponseEntity<Response> requestChangePassword(@RequestBody RequestVerifyEmail reqEmail) {
        Response response = new Response();
        String email = reqEmail.getEmail();
        try {
            User user = authService.findByEmail(email);
            if (!user.getEmail().equals(email)) throw new NoSuchFieldException("");
            authService.requestChangePassword(user);
            response.setResponse("success");
            response.setMessage("성공적으로 사용자의 비밀번호를 변경요청을 수행했습니다.");
            response.setData(null);
            logger.info("INFO SUCCESS");
            return new ResponseEntity<Response>(response, HttpStatus.OK);
        } catch (NoSuchFieldException e) {
            response.setResponse("error");
            response.setMessage("사용자의 정보를 조회할 수 없습니다.");
            response.setData(null);
            return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            response.setResponse("error");
            response.setMessage("비밀번호 변경 요청을 할 수 없습니다.");
            response.setData(null);
            throw new CustomException(ErrorCode.PASSWORD_VERIFY_ERROR);

        }
    }

    @PutMapping("/password")
    @ApiOperation(value = "비밀번호 변경", response = String.class)
    public ResponseEntity<User> changePassword(@RequestBody RequestChangePassword2 requestChangePassword2) {

        try {
            User user = authService.findByEmail(requestChangePassword2.getEmail());
            User savedUser = authService.changePassword(user, requestChangePassword2.getPassword());

            logger.info("INFO SUCCESS");
            return new ResponseEntity<User>(savedUser, HttpStatus.OK);
        } catch (Exception e) {

            throw new CustomException(ErrorCode.PASSWORD_VERIFY_ERROR);
           // return new ResponseEntity<User>((User) null, HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping("/signin") // 스켈레톤이랑 연결할땐 signin 지우고 '/' 상태에서
    @ApiOperation(value = "로그인 환경", response = Map.class)
    public ResponseEntity<Map<String, Object>> signin(@RequestBody User users,
                                                      HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        System.out.println("email pwd : "+users.getEmail()+" "+users.getPassword());
        System.out.println("secu : "+ SecurityContextHolder.getContext().getAuthentication());
        try {
            final User user = authService.signIn(users.getEmail(), users.getPassword());
            System.out.println("1pass");
            final String Token = jwtService.createToken(user.getEmail(), user.getIdentity());

            if (user.getIdentity() == UserIdentity.ROLE_UNAUTH) {
                status = HttpStatus.OK;
                Cookie profileCookie = new Cookie("profileid", "-1");
                resultMap.put("profileid", "-1");
                resultMap.put("status", HttpStatus.UNAUTHORIZED);
                resultMap.put("message", "권한이 없습니다.");

            } else {
                String profileid = String.valueOf(user.getProfile().getProfile_id());
                System.out.println("pid : " + profileid);

                System.out.println("accessToken : " + Token);

                Cookie accessToken = cookieService.createCookie(JwtServiceImpl.ACCESS_TOKEN_NAME, Token);
                Cookie profileCookie = new Cookie("profileid", profileid);
                Cookie identityCookie = new Cookie("identity", String.valueOf(user.getIdentity()));

                System.out.println("pass 2");
                response.addCookie(accessToken);
                response.addCookie(profileCookie);
                response.addCookie(identityCookie);

                System.out.println("pass 3");

                redisUtil.setData(user.getEmail(), Token);
                resultMap.put("accessToken", Token);
                resultMap.put("profileid", profileid);
                resultMap.put("Identity", user.getIdentity());
                resultMap.put("message", "success");
                status = HttpStatus.ACCEPTED;
                logger.info("INFO SUCCESS");
            }
        }

         catch (Exception e) {
            status = HttpStatus.UNAUTHORIZED;
            resultMap.put("message", "No Authorization");
        }

        System.out.println("status : "+status);
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/verify")
    @ApiOperation(value = "회원가입 인증", response = String.class)
    public ResponseEntity<String> verify(@RequestBody RequestVerifyEmail requestVerifyEmail, HttpServletRequest req, HttpServletResponse res) {

        System.out.println("ve : "+requestVerifyEmail.getEmail());
        try {
            User user = authService.findByEmail(requestVerifyEmail.getEmail());
            System.out.println("u : "+user.getName());
            String result = authService.sendVerificationMail(user);

            logger.info("INFO SUCCESS");
            return new ResponseEntity<String> (result, HttpStatus.OK);
        } catch (Exception exception) {

            throw new CustomException(ErrorCode.EMAIL_ERROR);
        }
    }

    @GetMapping("/verify/{key}")
    @ApiOperation(value = "회원가입 인증 확인")
    public ResponseEntity<User> getVerify(@PathVariable String key) {

        try {
            ResponseEntity<User> userResponseEntity = authService.verifyEmail(key);
            User user = userResponseEntity.getBody();
            authService.createProfile(user);

            logger.info("INFO SUCCESS");
            return new ResponseEntity<User>(user, HttpStatus.OK);

        } catch (Exception e) {

            throw new CustomException(ErrorCode.EMAIL_ERROR);

        }
    }
}
