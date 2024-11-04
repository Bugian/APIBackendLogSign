package com.example.service;

import java.util.HashMap;
import java.util.Map;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class UserService {
    private static final Map<String, UserInfo> users = new HashMap<>();
    private static final SecureRandom random = new SecureRandom();

    private static class UserInfo {
        String hashedPassword;
        String salt;

        UserInfo(String hashedPassword, String salt) {
            this.hashedPassword = hashedPassword;
            this.salt = salt;
        }
    }

    public boolean register(String username, String password) {
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Username and password must not be null or empty");
        }

        if (users.containsKey(username)) {
            return false; // Username already exists
        }

        String salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);
        users.put(username, new UserInfo(hashedPassword, salt));
        return true;
    }

    public boolean login(String username, String password) {
        if (username == null || password == null) {
            return false;
        }

        UserInfo userInfo = users.get(username);
        if (userInfo == null) {
            return false;
        }

        String hashedPassword = hashPassword(password, userInfo.salt);
        return hashedPassword.equals(userInfo.hashedPassword);
    }

    private String generateSalt() {
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
