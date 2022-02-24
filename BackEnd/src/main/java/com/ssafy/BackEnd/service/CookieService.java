package com.ssafy.BackEnd.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class CookieService {

    public Cookie createCookie(String cookieName, String value) {
        Cookie token = new Cookie(cookieName, value);
        token.setHttpOnly(true);
        //token.setMaxAge((int)JwtServiceImpl.TOKEN_VALIDATION_SECOND);
        token.setMaxAge(60 * 3);
        token.setPath("/");
        return token;
    }

    public Cookie getCookie(HttpServletRequest req, String cookieName) {
        final Cookie[] cookies = req.getCookies();
        if(cookies == null) return null;
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(cookieName)){
                System.out.println("쿠키 테스트욤");
                return cookie;
            }

        }
        return null;
    }

}
