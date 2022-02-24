package com.ssafy.BackEnd.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class SaltUtil {

    public String encodePassword(String salt, String password) {
        return BCrypt.hashpw(password, salt);
    }

    public String genSalt() {
        return BCrypt.gensalt();
    }

    public static boolean checkPassword(String hashed_password, String password) { return BCrypt.checkpw(password, hashed_password); }
}
