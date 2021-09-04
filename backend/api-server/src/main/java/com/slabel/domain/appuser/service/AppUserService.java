package com.slabel.domain.appuser.service;

import com.slabel.domain.appuser.domain.AppUser;

public interface AppUserService {
    void signUpUser(AppUser appUser);

    AppUser loginUser(String id, String password) throws Exception;
}
