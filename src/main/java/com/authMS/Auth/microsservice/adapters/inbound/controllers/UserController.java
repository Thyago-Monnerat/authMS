package com.authMS.Auth.microsservice.adapters.inbound.controllers;

import com.authMS.Auth.microsservice.application.useCases.IUserUseCases;
import com.authMS.Auth.microsservice.application.dtos.ChangePasswordDto;
import com.authMS.Auth.microsservice.application.dtos.UserLoginDto;
import com.authMS.Auth.microsservice.application.dtos.UserRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class UserController {

    private final IUserUseCases userService;

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto userLoginDto) {
        return ResponseEntity.ok().body(userService.getToken(userLoginDto));
    }

    @PatchMapping("new-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        return ResponseEntity.ok().body(userService.changePassword(changePasswordDto));
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDto userRegisterDto) {
        return ResponseEntity.status(201).body(userService.registerUser(userRegisterDto) + " registered successfully");
    }
}
