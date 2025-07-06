package com.authMS.Auth.microsservice.adapters.outbound.repositories;

import com.authMS.Auth.microsservice.domain.user.IUserRepository;
import com.authMS.Auth.microsservice.domain.user.UserModel;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements IUserRepository {

    private final IUserJpaRepository userJpaRepository;

    public UserRepositoryImpl(IUserJpaRepository repository) {
        this.userJpaRepository = repository;
    }

    @Override
    public Optional<UserModel> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }

    @Override
    public Optional<UserModel> findByUsername(String username) {
        return userJpaRepository.findByUsername(username);
    }

    @Override
    public void save(UserModel userModel) {
        userJpaRepository.save(userModel);
    }
}
