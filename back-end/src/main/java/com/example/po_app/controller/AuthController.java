package com.example.po_app.controller;

import com.example.po_app.model.User;
import com.example.po_app.security.JwtUtil;
import com.example.po_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;


    // Turns out this doesn't exist..
    
    // @PostMapping("/register")
    // public ResponseEntity<?> registerUser(@RequestBody User user) {
    //     User registeredUser = userService.registerUser(user);
    //     return ResponseEntity.ok(registeredUser);
    // }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            // Get the authenticated user's details
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Generate the JWT token using the authenticated username
            final String jwt = jwtUtil.generateToken(userDetails.getUsername());

            // Return the token as a response
            return ResponseEntity.ok(jwt);
        } catch (AuthenticationException e) {
            // Handle authentication failure (bad credentials)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
        }
    }

}
