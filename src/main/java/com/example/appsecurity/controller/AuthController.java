package com.example.appsecurity.controller;


import com.example.appsecurity.entity.User;
import com.example.appsecurity.request.RegisterRequest;
import com.example.appsecurity.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/api/")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("auth/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid  RegisterRequest request) {
          authService.register(request);
          return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "success"));

    }
}
