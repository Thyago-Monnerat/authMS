package com.authMS.Auth.microsservice.application.dtos;

import lombok.Data;

@Data
public class ChangePasswordDto {
    private String email;
    private String newPassword;
}
