package com.slabel.global.util;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class SaltUtil {
    public String encodePassword(String salt, String password) {
        System.out.println(salt);
        System.out.println(password);
        System.out.println(BCrypt.hashpw(password, salt));
        return BCrypt.hashpw(password, salt);
    }

    public String genSalt() {
        return BCrypt.gensalt();
    }
}
