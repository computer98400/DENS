package com.ssafy.BackEnd.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.BackEnd.exception.CustomException;
import com.ssafy.BackEnd.service.JwtServiceImpl;
import com.ssafy.BackEnd.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtServiceImpl jwtService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 헤더에서 JWT 를 받아옵니다.
        System.out.println("------------------filter---------------------");
        System.out.println("req : "+request);
        System.out.println("header : "+request);
        SecurityContextHolder.getContext().setAuthentication(null); // 로그아웃 때 사용하자
        String token = jwtService.resolveToken((HttpServletRequest) request);
        System.out.println("filter token : "+token);
//        String newToken = null;
//        if(token != null) newToken = token.substring(8, token.length()-1);

        //System.out.println("new token : "+token);

        // 유효한 토큰인지 확인합니다.
        if (token != null && jwtService.validateToken(token)) {
            try{
                Authentication auth = jwtService.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);

                System.out.println("intercept");
                System.out.println("secufilter : "+SecurityContextHolder.getContext().getAuthentication());
                // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
                //Authentication authentication = jwtService.getAuthentication(token);
//                System.out.println("auth1 : "+authentication.getName());
//                System.out.println("auth2 : "+authentication.getAuthorities());
//                System.out.println("auth3 : "+authentication.getDetails());
//                System.out.println("auth4 : "+authentication.getCredentials());
//                System.out.println("auth5 : "+authentication.getPrincipal());

                // SecurityContext 에 Authentication 객체를 저장합니다.
                //SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("validation token");
//                System.out.println("identity : "+jwtService.getUserRoles(token).get(0));
            } catch (CustomException e){
                System.out.println("error : "+e.getErrorCode());
                return;
            }
        }
        System.out.println();
        System.out.println("after secufilter : "+SecurityContextHolder.getContext().getAuthentication());
        chain.doFilter(request, response);
        System.out.println("after chain");
    }
}