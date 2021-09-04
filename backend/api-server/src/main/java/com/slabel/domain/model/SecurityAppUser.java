package com.slabel.domain.model;

import com.slabel.domain.appuser.domain.AppUser;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SecurityAppUser extends User {
    private final static long serialVersionUID = 1L;

    public SecurityAppUser(AppUser appUser) {
        super(appUser.getUsername(), "{noop}" + appUser.getPassword(), AuthorityUtils.createAuthorityList(appUser.getAppUserRole().toString()));
    }
}
