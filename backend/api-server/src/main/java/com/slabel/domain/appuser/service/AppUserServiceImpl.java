package com.slabel.domain.appuser.service;

import com.slabel.domain.appuser.dao.AppUserRepository;
import com.slabel.domain.appuser.domain.AppUser;
import com.slabel.domain.appuser.domain.Salt;
import com.slabel.domain.model.SecurityAppUser;
import com.slabel.domain.registration.dto.ConfirmationToken;
import com.slabel.domain.registration.service.ConfirmationTokenService;
import com.slabel.global.util.SaltUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserServiceImpl implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final AppUserRepository appUserRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    private final ConfirmationTokenService confirmationTokenService;
    private final SaltUtil saltUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser == null) {
            throw new UsernameNotFoundException(username + " : 사용자 없음");
        }
        return new SecurityAppUser(appUser);
    }

    public void signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository.findByEmail(appUser.getEmail()).isPresent();

        if (userExists) {
            throw new IllegalStateException("Email already taken");
        }

        String password = appUser.getPassword();
        String salt = saltUtil.genSalt();
        appUser.setSalt(new Salt(salt));
        appUser.setPassword(saltUtil.encodePassword(salt, password));

        appUserRepository.save(appUser);

//
//        if (userExists) {
//            // todo: check of attributes are the same and
//            // todo: if email not confirmed send confirmation email.
//
//            throw new IllegalStateException("email already taken");
//        }
//
//        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
//
//        appUser.setPassword(encodedPassword);
//
//        appUserRepository.save(appUser);
//
//        String token = UUID.randomUUID().toString();
//
//        ConfirmationToken confirmationToken = new ConfirmationToken(
//                token,
//                LocalDateTime.now(),
//                LocalDateTime.now().plusMinutes(15),
//                appUser
//        );
//
//        confirmationTokenService.saveConfirmationToken(confirmationToken);
//
//        // todo: send email
//
//        return token;
    }

    public AppUser loginUser(String username, String password) throws Exception {
        System.out.println("!");
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser == null) throw new Exception("유저 조회되지 않음");
        System.out.println("appuser" + appUser);
        String salt = appUser.getSalt().getSalt();
        System.out.println("salt" + salt);
        password = saltUtil.encodePassword(salt, password);
        System.out.println("passowrd" + password);
        System.out.println(password);

        System.out.println(appUser.getPassword());

        if (!appUser.getPassword().equals(password)) {
            throw new Exception("비밀번호가 틀립니다.");
        }
        return appUser;
    }
}
