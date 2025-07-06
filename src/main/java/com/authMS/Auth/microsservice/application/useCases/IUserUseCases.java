package com.authMS.Auth.microsservice.application.useCases;

import com.authMS.Auth.microsservice.application.dtos.ChangePasswordDto;
import com.authMS.Auth.microsservice.application.dtos.UserLoginDto;
import com.authMS.Auth.microsservice.application.dtos.UserRegisterDto;

public interface IUserUseCases {
    String registerUser(UserRegisterDto userRegisterDto);
    String changePassword(ChangePasswordDto changePasswordDto);
    String getToken(UserLoginDto userLoginDto);
}
