package com.hotel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hotel.dto.LoginRequest;
import com.hotel.dto.LoginResponse;
import com.hotel.dto.RegisterRequest;
import com.hotel.entity.User;
import com.hotel.enums.Role;
import com.hotel.jwt.JwtService;
import com.hotel.repository.UserRepository;
import com.hotel.security.CustomUserDetails;
import com.hotel.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Override
    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email already exists";
        }

        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Default role
        user.setRole(Role.CUSTOMER);

        userRepository.save(user);

        return "User Registered Successfully";

    }

    @Override
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow();

        CustomUserDetails userDetails =
                new CustomUserDetails(user);

        String token = jwtService.generateToken(userDetails);

        return new LoginResponse(
                token,
                "Login Successful");

    }

}