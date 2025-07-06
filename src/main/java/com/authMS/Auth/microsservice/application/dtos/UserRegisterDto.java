package com.authMS.Auth.microsservice.application.dtos;

import com.authMS.Auth.microsservice.domain.user.Role;
import lombok.Data;

@Data
public class UserRegisterDto {
    private String username;
    private String email;
    private String password;
    private Role role;
}
