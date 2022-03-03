package com.ssafy.BackEnd.filter;

import com.ssafy.BackEnd.exception.CustomException;
import com.ssafy.BackEnd.service.jwt.JwtServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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

        // 유효한 토큰인지 확인합니다.
        if (token != null && jwtService.validateToken(token)) {
            try{
                Authentication auth = jwtService.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);

                System.out.println("intercept");
                System.out.println("secufilter : "+SecurityContextHolder.getContext().getAuthentication());
                // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.

                // SecurityContext 에 Authentication 객체를 저장합니다.
                System.out.println("validation token");
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