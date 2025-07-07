package com.authMS.Auth.microsservice.adapters.outbound.repositories;

import com.authMS.Auth.microsservice.adapters.outbound.mappers.IUserMapper;
import com.authMS.Auth.microsservice.domain.user.IUserRepository;
import com.authMS.Auth.microsservice.domain.user.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements IUserRepository {

    private final IUserJpaRepository userJpaRepository;
    private final IUserMapper mapper;

    @Override
    public Optional<UserModel> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(mapper::fromJpaToModel);
    }

    @Override
    public Optional<UserModel> findByUsername(String username) {
        return userJpaRepository.findByUsername(username).map(mapper::fromJpaToModel);
    }

    @Override
    public void save(UserModel userModel) {
        userJpaRepository.save(mapper.fromModelToJpa(userModel));
    }
}
