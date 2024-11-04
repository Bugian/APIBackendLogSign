package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class RegisterRequest {
    @JsonProperty("email")
    private String email;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    // Default constructor
    public RegisterRequest() {
    }

    // Parameterized constructor
    public RegisterRequest(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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
        RegisterRequest that = (RegisterRequest) o;
        return Objects.equals(email, that.email) &&
               Objects.equals(username, that.username) &&
               Objects.equals(password, that.password);
    }

    // HashCode method
    @Override
    public int hashCode() {
        return Objects.hash(email, username, password);
    }

    // ToString method
    @Override
    public String toString() {
        return "RegisterRequest{" +
               "email='" + email + '\'' +
               ", username='" + username + '\'' +
               ", password='[PROTECTED]'" +
               '}';
    }
}
