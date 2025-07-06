package com.authMS.Auth.microsservice.application.services;

import com.authMS.Auth.microsservice.domain.messaging.IKafkaService;
import com.authMS.Auth.microsservice.domain.security.IJwtService;
import com.authMS.Auth.microsservice.application.useCases.IUserUseCases;
import com.authMS.Auth.microsservice.domain.security.IPasswordEncoder;
import com.authMS.Auth.microsservice.domain.user.IUserRepository;
import com.authMS.Auth.microsservice.application.dtos.ChangePasswordDto;
import com.authMS.Auth.microsservice.application.dtos.UserLogDto;
import com.authMS.Auth.microsservice.application.dtos.UserLoginDto;
import com.authMS.Auth.microsservice.application.dtos.UserRegisterDto;
import com.authMS.Auth.microsservice.infrastructure.exceptions.UserAlreadyRegistered;
import com.authMS.Auth.microsservice.infrastructure.exceptions.UserNotFound;
import com.authMS.Auth.microsservice.domain.user.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserUseCases {

    private final IUserRepository userRepository;
    private final IJwtService jwtService;
    private final IPasswordEncoder encoder;
    private final IKafkaService kafkaProducerService;

    @Override
    @Transactional
    public String registerUser(UserRegisterDto userRegisterDto) {
        userRepository.findByEmail(userRegisterDto.getEmail()).ifPresent((user) -> {
            throw new UserAlreadyRegistered("Email already registered");
        });

        userRepository.findByUsername(userRegisterDto.getUsername()).ifPresent((user) -> {
            throw new UserAlreadyRegistered("Username already registered");
        });


        userRegisterDto.setPassword(encoder.encode(userRegisterDto.getPassword()));

        UserModel user = new UserModel();

        BeanUtils.copyProperties(userRegisterDto, user);

        userRepository.save(user);

        kafkaProducerService.sendAuthLog(new UserLogDto(LocalDateTime.now(), user.getId(), user.getEmail(), user.getUsername()), "register");
        return userRegisterDto.getUsername();
    }

    @Override
    @Transactional
    public String changePassword(ChangePasswordDto changePasswordDto) {
        UserModel user = userRepository.findByEmail(changePasswordDto.getEmail()).orElseThrow(() -> new UserNotFound("Invalid Credentials"));

        String newPassword = encoder.encode(changePasswordDto.getNewPassword());

        user.setPassword(newPassword);

        userRepository.save(user);

        return "Password succesfully changed";
    }

    @Override
    @Transactional(readOnly = true)
    public String getToken(UserLoginDto userLoginDto) {
        UserModel user;

        if (userLoginDto.username().contains("@")) {
            user = userRepository.findByEmail(userLoginDto.username()).orElseThrow(() -> new UserNotFound("Invalid Credentials"));
        } else {
            user = userRepository.findByUsername(userLoginDto.username()).orElseThrow(() -> new UserNotFound("Invalid Credentials"));
        }

        if (!encoder.matches(userLoginDto.password(), user.getPassword())) {
            throw new UserNotFound("Invalid Credentials");
        }

        kafkaProducerService.sendAuthLog(new UserLogDto(LocalDateTime.now(), user.getId(), user.getEmail(), user.getUsername()), "login");
        return jwtService.generateToken(user);
    }
}
