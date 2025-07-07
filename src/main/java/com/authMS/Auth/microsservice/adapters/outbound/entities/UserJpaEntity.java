package com.authMS.Auth.microsservice.adapters.outbound.entities;

import com.authMS.Auth.microsservice.domain.user.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Role role;
}
