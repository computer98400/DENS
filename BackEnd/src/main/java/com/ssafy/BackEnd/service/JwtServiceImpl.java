package com.ssafy.BackEnd.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.persistence.Enumerated;
import javax.servlet.http.HttpServletRequest;

import com.ssafy.BackEnd.entity.User;
import com.ssafy.BackEnd.entity.UserIdentity;
import com.ssafy.BackEnd.exception.UnAuthorizedException;
import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl { //implements JwtService {

    private final CustomUserDetailService userDetailsService;

    public static final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);

    public final static long TOKEN_VALIDATION_SECOND = 1000L * 60 * 30;
    public final static long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 60 * 5;

    final static public String ACCESS_TOKEN_NAME = "accessToken";
    final static public String REFRESH_TOKEN_NAME = "refreshToken";

    @Value("${JWT.SECRET}")
    private String SALT;
//    private static final long EXPIRE_MINUTES = 30 * 60 *1000L;
    //private final ProfileService profileService;

    @PostConstruct
    protected void init() {
        SALT = Base64.getEncoder().encodeToString(SALT.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(String email, UserIdentity userIdentity) {
        Claims claims = Jwts.claims().setSubject(email); // JWT payload 에 저장되는 정보단위
        claims.put("identity", userIdentity); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + TOKEN_VALIDATION_SECOND)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, SALT)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserEmail(token));
        System.out.println("authority : "+userDetails.getAuthorities());
//        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        UsernamePasswordAuthenticationToken auth2 = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        auth2.setDetails(userDetails);
        auth2.eraseCredentials();
//        System.out.println("aut : "+auth);
        return auth2;
    }

    // 토큰에서 회원 정보 추출
    public String getUserEmail(String token) {
        return Jwts.parser().setSigningKey(SALT).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옵니다.
    public String resolveToken(HttpServletRequest req) {
        System.out.println("-------------resolvetk--------------");
        int cnt = 0;
        while (!req.getHeaderNames().hasMoreElements() || cnt <10){
            System.out.println("r header : "+req.getHeaderNames().nextElement());
            cnt++;
        }
        String token = req.getHeader("Authorization");
        String newToken = null;
//        while(req.getHeaderNames().hasMoreElements()){
//            System.out.println("name : "+req.getHeaderNames().nextElement());
//        }
//        System.out.println("header name : "+req.getHeaderNames());

        if(token != null) {
            newToken = token;
            //String[] strlist = newToken.split(" ");
            newToken = token.substring(8, token.length()-1);
            //System.out.println("newtk : " + strlist[1]);
            System.out.println("newtk : "+newToken);
        }
        return newToken;
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        System.out.println("----------validation------------");
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SALT).parseClaimsJws(jwtToken);
            System.out.println("jwttoken : "+jwtToken);
            System.out.println("claims : "+claims.getBody().getExpiration().getTime());
            System.out.println("date : "+claims.getBody().getExpiration().before(new Date()));
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            System.out.println("err : "+e.getMessage());
            System.out.println("false ");
            return false;
        }
    }

    public List<String> getUserRoles(String token) {
        return (List<String>) Jwts.parser().setSigningKey(SALT).parseClaimsJws(token).getBody().get("identity");
    }



//    protected void init() {
//        SALT = Base64.getEncoder().encodeToString(SALT.getBytes(StandardCharsets.UTF_8));
//    }
//
////    private Key getSigningKey(String secretKey){
////        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
////        return Keys.hmacShaKeyFor(keyBytes);
////    }
//
//    public String doGenerateToken(String email, UserIdentity identity){
//        Claims claims = Jwts.claims().setSubject(email);
//        //claims.put("email", email);
//        claims.put("identity", identity);
//        System.out.println("make token");
//
////        String jwt = Jwts.builder()
////                .setClaims(claims)  //정보저장
////                .setIssuedAt(new Date(System.currentTimeMillis())) // 현재 시간
////                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDATION_SECOND)) // 만료시간
////                .signWith(getSigningKey(SALT), SignatureAlgorithm.HS256) // 사용할알고리즘과 시그니쳐 secret값
////                .compact();
//
//        String jwt = Jwts.builder()
//                .setClaims(claims)  //정보저장
//                .setIssuedAt(new Date(System.currentTimeMillis())) // 현재 시간
//                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDATION_SECOND)) // 만료시간
//                .signWith(SignatureAlgorithm.HS256, SALT) // 사용할알고리즘과 시그니쳐 secret값
//                .compact();
//        return jwt;
//    }
//
////    public Claims extractAllClaims(String token) throws ExpiredJwtException {
////        Claims jwt = Jwts.parserBuilder()
////                .setSigningKey(getSigningKey(SALT))
////                .build()
////                .parseClaimsJws(token)
////                .getBody();
////
////        return jwt;
////    }
//
//    public String resolveToken(HttpServletRequest req) {
//        System.out.println("header : "+req.getHeader("Authorization"));
//        String token = req.getHeader("Authorization");
//        String newToken = null;
////        while(req.getHeaderNames().hasMoreElements()){
////            System.out.println("name : "+req.getHeaderNames().nextElement());
////        }
////        System.out.println("header name : "+req.getHeaderNames());
//        if(token != null) newToken = token.substring(8, token.length()-1);
//        return newToken;
//    }
//
//    public String getUserEmail(String token){
//        return Jwts.parser().setSigningKey(SALT).parseClaimsJws(token).getBody().getSubject();
//    }
////
////    public Boolean isTokenExpired(String token){
////        final Date expiraton = extractAllClaims(token).getExpiration();
////        return expiraton.before(new Date());
////    }
//
////    public String generateToken(User user) {
////        return doGenerateToken(user.getEmail(), TOKEN_VALIDATION_SECOND);
////    }
////
////    public String generateRefershToken(User user) {
////        return doGenerateToken(user.getEmail(), REFRESH_TOKEN_VALIDATION_SECOND);
////    }
//
//    public boolean validateToken(String jwtToken) {
//        //System.out.println("claim : "+Jwts.parserBuilder().setSigningKey(SALT).build().parseClaimsJws(jwtToken));
//        //System.out.println("claim : "+Jwts.parser().setSigningKey(SALT).parseClaimsJws(jwtToken));
//        try {
//
//            Jws<Claims> claims = Jwts.parser().setSigningKey(SALT).parseClaimsJws(jwtToken);
//            System.out.println("claims header : "+claims.getHeader());
//            return !claims.getBody().getExpiration().before(new Date());
//        } catch (Exception e) {
//            System.out.println("false ");
//            return false;
//        }
//    }
//
//    public Authentication getAuthentication(String token) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserEmail(token));
//        System.out.println("de size : "+userDetails.getAuthorities().size());
//        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//    }
//
////    public Boolean validateToken(String token, User user){
////        final String userEmail = getUserEmail(token);
////
////        return (userEmail.equals(user.getEmail()) && !isTokenExpired(token));
////    }
//
//    //    @Override
////    public <T> String create(String key, T data, String subject) {
////        //System.out.println("-------- hi create token-----------");
////        String jwt = Jwts.builder()
////                .setHeaderParam("typ", "JWT")
////                .setHeaderParam("regDate", System.currentTimeMillis())
////                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_MINUTES))
////                .setSubject(subject).claim(key, data)
////                .signWith(SignatureAlgorithm.HS256, this.generateKey()).compact();
////        //System.out.println("jwt : "+jwt);
////        return jwt;
////    }
//
////    private byte[] generateKey() {
////        byte[] key = null;
////        try {
////            key = SALT.getBytes("UTF-8");
////        } catch (UnsupportedEncodingException e) {
////            if (logger.isInfoEnabled()) {
////                e.printStackTrace();
////            } else {
////                logger.error("Making JWT Key Error ::: {}", e.getMessage());
////            }
////        }
////        return key;
////    }
//
////    public Authentication getAuthentication(String token){
////
////    }
//    //    전달 받은 토큰이 제대로 생성된것인지 확인 하고 문제가 있다면 UnauthorizedException을 발생.
////    @Override
////    public boolean isUsable(String jwt) {
////        try {
////            Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(jwt);
////            return true;
////        } catch (Exception e) {
//////            if (logger.isInfoEnabled()) {
//////                e.printStackTrace();
//////            } else {
////            logger.error(e.getMessage());
//////            }
//////            throw new UnauthorizedException();
//////            개발환경
////            return false;
////        }
////    }
//
////
////
////    @Override
////    public Map<String, Object> get(String key) {
////        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
////                .getRequest();
////        String jwt = request.getHeader("access-token");
////        Jws<Claims> claims = null;
////        try {
////            claims = Jwts.parser().setSigningKey(SALT.getBytes("UTF-8")).parseClaimsJws(jwt);
////        } catch (Exception e) {
//////            if (logger.isInfoEnabled()) {
//////                e.printStackTrace();
//////            } else {
////            logger.error(e.getMessage());
//////            }
////            throw new UnAuthorizedException();
//////            개발환경
//////            Map<String,Object> testMap = new HashMap<>();
//////            testMap.put("userid", userid);
//////            return testMap;
////        }
////        Map<String, Object> value = claims.getBody();
////        logger.info("value : {}", value);
////        return value;
////    }
////
////    @Override
////    public String getUserId() {
////        return (String) this.get("user").get("userid");
////    }
}