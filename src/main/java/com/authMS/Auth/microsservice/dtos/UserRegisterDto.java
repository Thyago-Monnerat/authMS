package com.authMS.Auth.microsservice.dtos;

import com.authMS.Auth.microsservice.enums.Role;
import lombok.Data;

@Data
public class UserRegisterDto {
    private String username;
    private String email;
    private String password;
    private Role role;
}
