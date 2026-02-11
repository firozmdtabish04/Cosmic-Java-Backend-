package com.example.cosmic.cosmic.service;

import java.time.LocalDate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cosmic.cosmic.dto.*;
import com.example.cosmic.cosmic.model.User;
import com.example.cosmic.cosmic.repository.UserRepository;
import com.example.cosmic.cosmic.security.JwtUtil;

@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository repo, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    /* ================= SIGNUP ================= */

    public String signup(SignupRequest req) {

        if (repo.findByEmail(req.email).isPresent())
            throw new RuntimeException("Email already exists");

        User user = new User();
        user.setEmail(req.email);
        user.setUsername(req.username);
        user.setPassword(encoder.encode(req.password));
        user.setPhoneNumber(req.phoneNumber);
        user.setDob(req.dob);
        user.setJoinedDate(LocalDate.now());

        repo.save(user);

        return "User registered successfully";
    }

    /* ================= LOGIN ================= */

    public LoginResponse login(LoginRequest req) {

        User user = repo.findByEmail(req.email)
                .orElseThrow(() -> new RuntimeException("Invalid email"));

        if (!encoder.matches(req.password, user.getPassword()))
            throw new RuntimeException("Invalid password");

        String token = jwtUtil.generateToken(user.getEmail());

        return new LoginResponse(
                token,
                user.getEmail(),
                user.getUsername(),
                user.getPhoneNumber(),
                user.getDob(),
                user.getJoinedDate()
        );
    }

    /* ================= UPDATE PROFILE ================= */

    public User updateProfile(String email, UpdateProfileRequest req) {

        User user = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(req.username);
        user.setPhoneNumber(req.phoneNumber);
        user.setDob(req.dob);
        user.setAvatar(req.avatar);
        user.setBio(req.bio);
        user.setLocation(req.location);
        user.setGithub(req.github);
        user.setLinkedin(req.linkedin);

        return repo.save(user);
    }

    /* ================= GET PROFILE ================= */

    public User getProfile(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
