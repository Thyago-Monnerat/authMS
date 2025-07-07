package com.authMS.Auth.microsservice.adapters.outbound.mappers;

import com.authMS.Auth.microsservice.adapters.outbound.entities.UserJpaEntity;
import com.authMS.Auth.microsservice.domain.user.UserModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    UserModel fromJpaToModel(UserJpaEntity userJpa);
    UserJpaEntity fromModelToJpa(UserModel user);
}
