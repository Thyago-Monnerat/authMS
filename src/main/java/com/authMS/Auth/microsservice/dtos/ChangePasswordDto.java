package com.authMS.Auth.microsservice.dtos;

import lombok.Data;

@Data
public class ChangePasswordDto {
    private String email;
    private String newPassword;
}
