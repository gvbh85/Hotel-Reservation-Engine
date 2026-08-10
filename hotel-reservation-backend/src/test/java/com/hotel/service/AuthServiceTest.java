package com.hotel.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.hotel.dto.RegisterRequest;
import com.hotel.entity.User;
import com.hotel.repository.UserRepository;
import com.hotel.service.impl.AuthServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    AuthServiceImpl authService;

    @Test
    void testRegister() {

        RegisterRequest request = new RegisterRequest();

        request.setFullName("Hari");
        request.setEmail("hari@gmail.com");
        request.setPassword("123456");

        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(false);

        when(passwordEncoder.encode("123456"))
                .thenReturn("encrypted");

        String response = authService.register(request);

        assertEquals("User Registered Successfully", response);

        verify(userRepository, times(1))
                .save(any(User.class));

    }

}