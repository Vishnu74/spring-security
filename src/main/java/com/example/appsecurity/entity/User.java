package com.example.appsecurity.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Identity
    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    // Authorization
   @ManyToMany(fetch = FetchType.EAGER)
   @JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id"),
   inverseJoinColumns = @JoinColumn(name = "role_id"))
   private Set<Role> roles = new HashSet<>();

    // Account Status
    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = false)
    private boolean accountNonLocked = true;

    private int failedAttempt;

    private LocalDateTime lockTime;

    // Audit
    private LocalDateTime createdDateTime;

    private LocalDateTime updatedDateTime;

    private LocalDateTime lastLogin;
}

