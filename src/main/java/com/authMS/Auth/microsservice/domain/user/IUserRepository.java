package com.authMS.Auth.microsservice.domain.user;

import java.util.Optional;

public interface IUserRepository {
    Optional<UserModel> findByEmail(String email);
    Optional<UserModel> findByUsername(String username);
    void save(UserModel userModel);
}
