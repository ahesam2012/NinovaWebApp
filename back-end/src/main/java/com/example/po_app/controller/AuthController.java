// src/main/java/com/example/po_app/controller/AuthController.java

package com.example.po_app.controller;

import com.example.po_app.response.LoginResponse;
import com.example.po_app.response.RegisterResponse;
import com.example.po_app.security.JwtUtil;
import com.example.po_app.service.UserService;

import jakarta.validation.Valid;

import com.example.po_app.model.AuthRequest;
import com.example.po_app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    // Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@Valid @RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(),
                    authRequest.getPassword()
                )
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new LoginResponse(
                    "error",
                    HttpStatus.UNAUTHORIZED.value(),
                    "Invalid username or password",
                    null,
                    null,
                    LocalDateTime.now(),
                    false
                )
            );
        }
        final UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(authRequest.getUsername());
        final String role = userDetails.getAuthorities().iterator().next().getAuthority().replace("ROLE_", ""); // Remove "ROLE_"

        return ResponseEntity.ok(
            new LoginResponse(
                "success",
                HttpStatus.OK.value(),
                null,
                jwt,
                role,
                LocalDateTime.now(),
                true
            )
        );
    }

    // Register Endpoint
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@Valid @RequestBody AuthRequest authRequest, BindingResult bindingResult) {
        // Handle validation errors
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors()
                                               .stream()
                                               .map(error -> error.getDefaultMessage())
                                               .findFirst()
                                               .orElse("Invalid input");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new RegisterResponse(
                    "error",
                    HttpStatus.BAD_REQUEST.value(),
                    errorMessage,
                    LocalDateTime.now(),
                    false
                )
            );
        }

        // Check if username already exists
        if (userService.findByUsername(authRequest.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new RegisterResponse(
                    "error",
                    HttpStatus.CONFLICT.value(),
                    "Username already exists",
                    LocalDateTime.now(),
                    false
                )
            );
        }

        // Create new user
        User newUser = new User();
        newUser.setUsername(authRequest.getUsername());
        newUser.setPassword(authRequest.getPassword());
        newUser.setRole(authRequest.getRole()); // Assign default role

        userService.saveUser(newUser);
        return ResponseEntity.ok(
            new RegisterResponse(
                "success",
                HttpStatus.OK.value(),
                "User registered successfully",
                LocalDateTime.now(),
                true
            )
        );
    }
}
