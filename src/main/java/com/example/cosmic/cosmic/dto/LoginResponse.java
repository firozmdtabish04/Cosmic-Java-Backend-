package com.example.cosmic.cosmic.dto;

import java.time.LocalDate;

public class LoginResponse {

    private String token;
    private String email;
    private String username;
    private String phoneNumber;
    private LocalDate dob;
    private LocalDate joinedDate;

    public LoginResponse(String token, String email, String username,
                         String phoneNumber, LocalDate dob, LocalDate joinedDate) {
        this.token = token;
        this.email = email;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.joinedDate = joinedDate;
    }

    public String getToken() { return token; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getPhoneNumber() { return phoneNumber; }
    public LocalDate getDob() { return dob; }
    public LocalDate getJoinedDate() { return joinedDate; }
}
