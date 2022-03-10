package com.ssafy.BackEnd.service.jwt;

import java.util.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import com.ssafy.BackEnd.entity.UserIdentity;
import com.ssafy.BackEnd.service.user.CustomUserDetailService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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
        UsernamePasswordAuthenticationToken auth2 = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        auth2.setDetails(userDetails);
        auth2.eraseCredentials();

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
        String token = req.getHeader("Authorization");
        String newToken = null;

        if(token != null) {
            newToken = token.substring(8, token.length()-1);
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
}