package com.slabel.domain.appuser.api;

import com.slabel.domain.appuser.dao.AppUserRepository;
import com.slabel.domain.appuser.domain.AppUser;
import com.slabel.domain.appuser.service.AppUserService;
import com.slabel.domain.appuser.service.AppUserServiceImpl;
import com.slabel.domain.model.Response;
import com.slabel.domain.model.request.RequestLoginUser;
import com.slabel.domain.registration.dto.RegistrationRequest;
import com.slabel.global.util.CookieUtil;
import com.slabel.global.util.JwtUtil;
import com.slabel.global.util.RedisUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "api/v1/auth")
@AllArgsConstructor
public class AppUserController {

    @Autowired
    private AppUserServiceImpl appUserServiceImpl;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CookieUtil cookieUtil;
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    public AppUserController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Response login(@RequestBody RequestLoginUser user,
                          HttpServletRequest req,
                          HttpServletResponse res) {
        try {
            AppUser appUser = appUserServiceImpl.loginUser(user.getUsername(), user.getPassword());
            final String token = jwtUtil.generateToken(appUser);
            final String refreshJwt = jwtUtil.generateRefreshToken(appUser);
            Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, token);
            Cookie refreshToken = cookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, refreshJwt);
            redisUtil.setDataExpire(refreshJwt, appUser.getUsername(), JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);
            res.addCookie(accessToken);
            res.addCookie(refreshToken);

            return new Response("success", "로그인에 성공했습니다.", token);
        } catch (Exception e) {
            return new Response("error", "로그인에 실패했습니다.", e.getMessage());
        }
    }
}