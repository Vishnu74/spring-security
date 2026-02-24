package com.example.appsecurity.repo;

import com.example.appsecurity.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(@NotBlank String email);

    boolean existsByEmail(@Email String email);

    boolean existsByUsername(@NotBlank String username);

    Optional<User> findByUsername(String username);
}
