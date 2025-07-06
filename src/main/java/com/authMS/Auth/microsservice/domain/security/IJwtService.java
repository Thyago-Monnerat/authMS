package com.authMS.Auth.microsservice.domain.security;

import com.authMS.Auth.microsservice.domain.user.UserModel;

public interface IJwtService {
    String generateToken(UserModel user);
}
