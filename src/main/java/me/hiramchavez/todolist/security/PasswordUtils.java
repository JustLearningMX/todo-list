package me.hiramchavez.todolist.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String generatePasswordHash(String password) {
        return encoder.encode(password);
    }

    public static boolean verifyPassword(String password, String encodedPassword) {
        return encoder.matches(password, encodedPassword);
    }
}
