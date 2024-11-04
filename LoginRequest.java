package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class LoginRequest {
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    // Default constructor
    public LoginRequest() {
    }

    // Parameterized constructor
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginRequest that = (LoginRequest) o;
        return Objects.equals(username, that.username) &&
               Objects.equals(password, that.password);
    }

    // HashCode method
    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    // ToString method
    @Override
    public String toString() {
        return "LoginRequest{" +
               "username='" + username + '\'' +
               ", password='[PROTECTED]'" +
               '}';
    }
}
