package com.slabel.domain.registration.service;

import com.slabel.domain.appuser.domain.AppUser;
import com.slabel.domain.appuser.domain.AppUserRole;
import com.slabel.domain.appuser.service.AppUserService;
import com.slabel.domain.registration.dto.RegistrationRequest;
import com.slabel.infra.email.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        return appUserService.signUpUser(
                new AppUser(
                        request.getName(),
                        request.getUsername(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.User
                )
        );
    }
}
