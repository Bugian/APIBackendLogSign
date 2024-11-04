package com.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.example.model.ApiResponse;
import com.example.model.LoginRequest;
import com.example.model.RegisterRequest;
import com.example.service.TokenGenerator;
import com.example.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.HashMap;

public class Handler implements RequestHandler<Map<String, Object>, ApiResponse> {

    private final UserService userService = new UserService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ApiResponse handleRequest(Map<String, Object> input, Context context) {
        LambdaLogger logger = context.getLogger();
        String httpMethod = (String) input.get("httpMethod");
        String path = (String) input.get("path");

        try {
            if ("GET".equalsIgnoreCase(httpMethod)) {
                if ("/health".equalsIgnoreCase(path)) {
                    return handleHealthCheck();
                }
            } else if ("POST".equalsIgnoreCase(httpMethod)) {
                if ("/login".equalsIgnoreCase(path)) {
                    return handleLogin(input, logger);
                } else if ("/register".equalsIgnoreCase(path)) {
                    return handleRegister(input, logger);
                }
            }
            return createResponse(404, "Path not found");
        } catch (Exception e) {
            logger.log("Error processing request: " + e.getMessage());
            return createResponse(500, "Internal server error");
        }
    }

    private ApiResponse handleLogin(Map<String, Object> input, LambdaLogger logger) throws Exception {
        LoginRequest loginRequest = objectMapper.readValue((String) input.get("body"), LoginRequest.class);
        
        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            return createResponse(400, "Username and password are required");
        }

        boolean success = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (success) {
            String token = TokenGenerator.generateToken(loginRequest.getUsername());
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "Login successful");
            responseBody.put("token", token);
            return createResponse(200, objectMapper.writeValueAsString(responseBody));
        } else {
            return createResponse(401, "Invalid credentials");
        }
    }


    private ApiResponse handleRegister(Map<String, Object> input, LambdaLogger logger) throws Exception {
        try {
            logger.log("Received registration request");
            String body = (String) input.get("body");
            logger.log("Request body: " + body);
            
            RegisterRequest registerRequest = objectMapper.readValue(body, RegisterRequest.class);
            logger.log("Parsed RegisterRequest: " + registerRequest);
            
            if (registerRequest.getUsername() == null || registerRequest.getPassword() == null || registerRequest.getEmail() == null) {
                logger.log("Missing required fields");
                return createResponse(400, "Username, password, and email are required");
            }
    
            logger.log("Attempting to register user: " + registerRequest.getUsername());
            boolean success = userService.register(registerRequest.getUsername(), registerRequest.getPassword());
            if (success) {
                logger.log("Registration successful");
                String token = TokenGenerator.generateToken(registerRequest.getUsername());
                Map<String, String> responseBody = new HashMap<>();
                responseBody.put("message", "Registration successful");
                responseBody.put("token", token);
                return createResponse(201, objectMapper.writeValueAsString(responseBody));
            } else {
                logger.log("Registration failed: Username already exists");
                return createResponse(409, "Username already exists");
            }
        } catch (Exception e) {
            logger.log("Error during registration: " + e.getMessage());
            e.printStackTrace();
            return createResponse(500, "Internal server error: " + e.getMessage());
        }
    }
    
    


    private ApiResponse handleHealthCheck() {
        return createResponse(200, "Service is healthy");
    }

    private ApiResponse createResponse(int statusCode, String body) {
        ApiResponse response = new ApiResponse();
        response.setStatusCode(statusCode);
        response.setBody(body);
        return response;
    }
}
