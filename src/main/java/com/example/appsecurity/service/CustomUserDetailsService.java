package com.example.appsecurity.service;

import com.example.appsecurity.entity.User;
import com.example.appsecurity.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
    public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
// fetch user from the database.
    //role into Granted Authority
    //return supring security into objects

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                user.isAccountNonLocked(),
                user.getRoles()
                        .stream()
                        .map(role ->
                                new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toSet())
        );
    }





}
