package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;

public class ApiResponse {
    @JsonProperty("statusCode")
    private int statusCode;

    @JsonProperty("body")
    private String body;

    @JsonProperty("headers")
    private Map<String, String> headers;

    // Default constructor
    public ApiResponse() {
        this.statusCode = 200;
        this.body = "";
        this.headers = new HashMap<>();
        this.headers.put("Content-Type", "application/json");
    }

    // Parameterized constructor
    public ApiResponse(int statusCode, String body) {
        this();
        this.statusCode = statusCode;
        this.body = body;
    }

    // Getters and setters
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    // Method to add a single header
    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "statusCode=" + statusCode +
                ", body='" + body + '\'' +
                ", headers=" + headers +
                '}';
    }
}
