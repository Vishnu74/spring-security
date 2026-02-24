package com.example.appsecurity.service;

import com.example.appsecurity.config.PasswordEncoderConfig;
import com.example.appsecurity.dto.LoginRequestDto;
import com.example.appsecurity.entity.Role;
import com.example.appsecurity.entity.User;
import com.example.appsecurity.exception.BusinessException;
import com.example.appsecurity.repo.RoleRepository;
import com.example.appsecurity.repo.UserRepository;
import com.example.appsecurity.request.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoderConfig passwordEncoder;
    private final AuthenticationManager authenticationManager;



    public void register(RegisterRequest registerRequest) {
        if(userRepository.existsByEmail(registerRequest.getEmail()))
        {
        throw new BusinessException("Email already exists",HttpStatus.CONFLICT);
        }
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new BusinessException("Username alredy exsits",HttpStatus.CONFLICT);
        }
        Role role = roleRepository.findByName("ROLE_CUSTOMER").orElseThrow(() -> new RuntimeException("Role not found"));
        User user = new User();
        user.setUsername(registerRequest.getEmail());
        user.setPassword(passwordEncoder.passwordEncoder().encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setEnabled(true);
        user.setFailedAttempt(0);
        user.setCreatedDateTime(LocalDateTime.now());
        user.getRoles().add(role);
        userRepository.save(user);


    }


    public String login(@Valid LoginRequestDto dto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "Login successful";

    }
}
