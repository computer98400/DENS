package com.ssafy.BackEnd.controller;

import com.ssafy.BackEnd.dto.UserDto;
import com.ssafy.BackEnd.entity.*;
import com.ssafy.BackEnd.entity.Request.RequestChangePassword1;
import com.ssafy.BackEnd.entity.Request.RequestChangePassword2;
import com.ssafy.BackEnd.entity.Request.RequestVerifyEmail;
import com.ssafy.BackEnd.exception.CustomException;
import com.ssafy.BackEnd.exception.ErrorCode;
import com.ssafy.BackEnd.service.*;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {
    private static final Logger logger = LogManager.getLogger(MainController.class);

    private final JwtServiceImpl jwtService;
    private final CookieService cookieService;
    private final RedisUtil redisUtil;
    
    private final AuthService authService;

    private final dummyService dummyService;
    private final TeamService teamService;
    private final ProfileService profileService;


    @GetMapping("test11")
    public ResponseEntity<Map<String, Object>> test11() {
        //System.out.println("11 :"+header);
        logger.info("test11");
        System.out.println("teset11이에요");
        Map<String, Object> map = new HashMap<>();
        map.put("message", "test11");
        map.put("success", "성공");

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/test33")
    public ResponseEntity<Map<String, Object>> test33(@RequestHeader String header ) {
        System.out.println("33 : "+header);
        logger.info("test33");
        System.out.println("teset33이에요");
        Map<String, Object> map = new HashMap<>();
        map.put("message", "test33");
        map.put("success", "성공");

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/test44")
    public ResponseEntity<Map<String, Object>> test44(@RequestHeader String header ) {
        System.out.println("44 : "+header);
        logger.info("test44");
        System.out.println("teset44이에요");
        Map<String, Object> map = new HashMap<>();
        map.put("message", "test44");
        map.put("success", "성공");

        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @PostMapping("/signup")
    @ApiOperation(value = "회원가입", notes = "사용자의 정보를 입력 받고 'success'면 회원가입 or 'fail이면 에러메세지", response = String.class)
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody UserDto userDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        System.out.println("up : "+userDto.getEmail());
        try {
//            User user = userDto.createUser();
            authService.signUp(userDto);
//            if (authService.validateDuplicateUser(user)==false) {
//                status = HttpStatus.IM_USED;
//                resultMap.put("message", "이미 존재하는 회원입니다.");
//                return new ResponseEntity<Map<String, Object>>(resultMap, status);
//            }

            System.out.println("userpwd : "+userDto.getPassword());
            status = HttpStatus.ACCEPTED;
            resultMap.put("message", "success");
            logger.info("INFO SUCCESS");
        }
        catch(Exception e) {
            status = HttpStatus.ACCEPTED;
            throw new CustomException(ErrorCode.SIGNUP_ERROR);
        }
        //return response;
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
            //return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
            throw new CustomException("invalid key", ErrorCode.PASSWORD_VERIFY_ERROR);
        }
    }

    @GetMapping("/search/keyword/{param}")
    public ResponseEntity<List<dummy>> searchKeyword(@PathVariable String param) {

        System.out.println("param : "+param);

        if (param == null) {
            dummy temp = new dummy();
            temp.setId(45);
            temp.setName("name");
            List<dummy> senddata = new ArrayList<>();
            senddata.add(temp);
            return new ResponseEntity<List<dummy>>(senddata, HttpStatus.OK);
        } else {
            List<dummy> search_teams = dummyService.searchTestCheck(param);
            return new ResponseEntity<List<dummy>>(search_teams, HttpStatus.OK);
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
            //return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/password")
    @ApiOperation(value = "비밀번호 변경", response = String.class)
    public ResponseEntity<User> changePassword(@RequestBody RequestChangePassword2 requestChangePassword2) {
        Response response = new Response();
        try {
            User user = authService.findByEmail(requestChangePassword2.getEmail());
            User savedUser = authService.changePassword(user, requestChangePassword2.getPassword());
            response.setResponse("success");
            response.setMessage("사용자의 비밀번호를 성공적으로 변경했습니다.");
            response.setData(null);
            logger.info("INFO SUCCESS");
            return new ResponseEntity<User>(savedUser, HttpStatus.OK);
        } catch (Exception e) {
            response.setResponse("error");
            response.setMessage("사용자의 비밀번호를 변경할 수 없습니다.");
            response.setData(null);
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
            //System.out.println(user.getEmail()+" "+user.getPassword());
            //System.out.println("user null ? "+user);
//            if(user != null) {
            System.out.println("1pass");
            final String Token = jwtService.createToken(user.getEmail(), user.getIdentity());
            //Profile profile = profileService.findbyEmail(user.getEmail());
            //System.out.println("pid : "+profile.getProfile_id());

            if (user.getIdentity() == UserIdentity.ROLE_UNAUTH) {
                status = HttpStatus.OK;
                Cookie profileCookie = new Cookie("profileid", "-1");
                resultMap.put("profileid", "-1");
                resultMap.put("status", HttpStatus.UNAUTHORIZED);
                resultMap.put("message", "권한이 없습니다.");

            } else {
                String profileid = String.valueOf(user.getProfile().getProfile_id());
                System.out.println("pid : " + profileid);
                //final String refreshJwt = jwtService.generateRefershToken(user);

                System.out.println("accessToken : " + Token);
                //System.out.println("refreshToken : "+refreshJwt);

                Cookie accessToken = cookieService.createCookie(JwtServiceImpl.ACCESS_TOKEN_NAME, Token);
                Cookie profileCookie = new Cookie("profileid", profileid);
                Cookie identityCookie = new Cookie("identity", String.valueOf(user.getIdentity()));
//                Cookie refreshToken = cookieService.createCookie(JwtServiceImpl.REFRESH_TOKEN_NAME, refreshJwt);

                System.out.println("pass 2");
                response.addCookie(accessToken);
                response.addCookie(profileCookie);
                response.addCookie(identityCookie);
                //response.addCookie();
//                response.addCookie(refreshToken);

                System.out.println("pass 3");
                //redisUtil.setDataExpire(Token, user.getEmail(), JwtServiceImpl.REFRESH_TOKEN_VALIDATION_SECOND);

                redisUtil.setData(user.getEmail(), Token);
                resultMap.put("accessToken", Token);
                resultMap.put("profileid", profileid);
                resultMap.put("Identity", user.getIdentity());
                resultMap.put("message", "success");
                status = HttpStatus.ACCEPTED;
                logger.info("INFO SUCCESS");
            }
        }
//        else {
//                //System.out.println("error");
//                resultMap.put("message", "fail");
//                status = HttpStatus.UNAUTHORIZED;
//            }
         catch (Exception e) {
            status = HttpStatus.UNAUTHORIZED;
//            resultMap.put("message", e.getMessage());
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
            resultMap.put("message", "No Authorization");
            //throw new CustomException(ErrorCode.INVALID_ID);
        }

        System.out.println("status : "+status);
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/verify")
    @ApiOperation(value = "회원가입 인증", response = String.class)
    public ResponseEntity<String> verify(@RequestBody RequestVerifyEmail requestVerifyEmail, HttpServletRequest req, HttpServletResponse res) {
        Response response = new Response();
        System.out.println("ve : "+requestVerifyEmail.getEmail());
        try {
            User user = authService.findByEmail(requestVerifyEmail.getEmail());
            System.out.println("u : "+user.getName());
            String result = authService.sendVerificationMail(user);

            response.setResponse("success");
            response.setMessage("성공적으로 인증메일을 보냈습니다");
            response.setData(null);
            logger.info("INFO SUCCESS");
            return new ResponseEntity<String> (result, HttpStatus.OK);
        } catch (Exception exception) {
            //response = new Response("error", "인증메일을 보내는데 문제가 발생했습니다.", exception);
            response.setResponse("error");
            response.setMessage("인증메일을 보내는데 문제가 발생했습니다");
            response.setData(exception);
            throw new CustomException(ErrorCode.EMAIL_ERROR);
        }
    }

    @GetMapping("/verify/{key}")
    @ApiOperation(value = "회원가입 인증 확인")
    public ResponseEntity<User> getVerify(@PathVariable String key) {
        Response response;
        try {
            ResponseEntity<User> userResponseEntity = authService.verifyEmail(key);
            User user = userResponseEntity.getBody();
            authService.createProfile(user);
            response = new Response("success", "성공적으로 인증메일을 확인했습니다.", null);
            logger.info("INFO SUCCESS");
            return new ResponseEntity<User>(user, HttpStatus.OK);

        } catch (Exception e) {
            response = new Response("error", "인증메일을 확인하는데 실패했습니다.", null);
            throw new CustomException(ErrorCode.EMAIL_ERROR);
            //return new ResponseEntity<User>((User) null, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/headertest")
    public ResponseEntity<Map<String, Object>> checkHeader(HttpServletRequest request) {
        System.out.println("header : "+request.getHeader("Authorization"));
//        System.out.println(request.getHeader("Authorization").getClass());
//        System.out.println(request.getAttribute("check").getClass());

//        String validationCheck = request.getAttribute("check").toString();
//        System.out.println(validationCheck);
        //System.out.println(request.getAttribute());
//        for(Cookie cookie : request.getCookies()){
//            System.out.println(cookie.getValue());
//        }

        HttpStatus status;
        Map<String, Object> map = new HashMap<>();

        if(true){
            map.put("message", "fail");
            map.put("test", "데이터가 없습니다");
            status = HttpStatus.OK;
            logger.info("INFO SUCCESS");
        } else {
            map.put("message", "success");
            map.put("test", "데이터 받기 성공");
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(map, status);
    }
}
