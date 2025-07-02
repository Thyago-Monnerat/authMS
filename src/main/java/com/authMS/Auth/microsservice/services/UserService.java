package com.authMS.Auth.microsservice.services;

import com.authMS.Auth.microsservice.dtos.ChangePasswordDto;
import com.authMS.Auth.microsservice.dtos.UserLogDto;
import com.authMS.Auth.microsservice.dtos.UserLoginDto;
import com.authMS.Auth.microsservice.dtos.UserRegisterDto;
import com.authMS.Auth.microsservice.exceptions.UserAlreadyRegistered;
import com.authMS.Auth.microsservice.exceptions.UserNotFound;
import com.authMS.Auth.microsservice.models.UserModel;
import com.authMS.Auth.microsservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder encoder;
    private final KafkaProducerService kafkaProducerService;

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

        kafkaProducerService.authLogsSender(new UserLogDto(LocalDateTime.now(), user.getId(), user.getEmail(), user.getUsername()), "register");
        return userRegisterDto.getUsername();
    }

    @Transactional
    public String changePassword(ChangePasswordDto changePasswordDto) {
        UserModel user = userRepository.findByEmail(changePasswordDto.getEmail()).orElseThrow(() -> new UserNotFound("Invalid Credentials"));

        String newPassword = encoder.encode(changePasswordDto.getNewPassword());

        user.setPassword(newPassword);

        userRepository.save(user);

        return "Password succesfully changed";
    }

    public String getLoginToken(UserLoginDto userLoginDto) {
        UserModel user;

        if (userLoginDto.username().contains("@")) {
            user = userRepository.findByEmail(userLoginDto.username()).orElseThrow(() -> new UserNotFound("Invalid Credentials"));
        } else {
            user = userRepository.findByUsername(userLoginDto.username()).orElseThrow(() -> new UserNotFound("Invalid Credentials"));
        }

        if (!BCrypt.checkpw(userLoginDto.password(), user.getPassword())) {
            throw new UserNotFound("Invalid Credentials");
        }

        kafkaProducerService.authLogsSender(new UserLogDto(LocalDateTime.now(), user.getId(), user.getEmail(), user.getUsername()), "login");
        return jwtService.generateToken(user);
    }
}
