package com.slabel.global.util;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class CookieUtil {
    public Cookie createCookie(String cookieName, String value) {
        System.out.println("createCookie");
        System.out.println(cookieName + " " + value);
        Cookie token = new Cookie(cookieName, value);
        System.out.println(token);
        token.setHttpOnly(true);
        System.out.println("1");
        token.setMaxAge((int)JwtUtil.TOKEN_VALIDATION_SECOND);
        System.out.println("2");
        token.setPath("/");
        System.out.println("3");
        return token;
    }

    public Cookie getCookie(HttpServletRequest req, String cookieName) {
        final Cookie[] cookies = req.getCookies();
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName))
                return cookie;
        }
        return null;
    }
}
