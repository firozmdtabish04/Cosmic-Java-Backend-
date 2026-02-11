package com.example.cosmic.cosmic.controller;

import org.springframework.web.bind.annotation.*;

import com.example.cosmic.cosmic.dto.*;
import com.example.cosmic.cosmic.model.User;
import com.example.cosmic.cosmic.security.JwtUtil;
import com.example.cosmic.cosmic.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final AuthService service;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    /* ================= SIGNUP ================= */

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest req) {
        return service.signup(req);
    }

    /* ================= LOGIN ================= */

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {
        return service.login(req);
    }

    /* ================= UPDATE PROFILE ================= */

    @PutMapping("/profile")
    public User updateProfile(
            @RequestBody UpdateProfileRequest req,
            @RequestHeader("Authorization") String token) {

        String email = jwtUtil.extractUsername(token.substring(7));
        return service.updateProfile(email, req);
    }

    /* ================= GET PROFILE ================= */

    @GetMapping("/profile")
    public User getProfile(@RequestHeader("Authorization") String token) {

        String email = jwtUtil.extractUsername(token.substring(7));
        return service.getProfile(email);
    }
}
