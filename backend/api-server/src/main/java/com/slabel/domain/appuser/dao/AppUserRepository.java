package com.slabel.domain.appuser.dao;

import com.slabel.domain.appuser.domain.AppUser;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository {

    Optional<AppUser> findByEmail(String email);
}
