package com.authMS.Auth.microsservice.controllers;

import com.authMS.Auth.microsservice.dtos.ChangePasswordDto;
import com.authMS.Auth.microsservice.dtos.UserLoginDto;
import com.authMS.Auth.microsservice.dtos.UserRegisterDto;
import com.authMS.Auth.microsservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto userLoginDto) {
        return ResponseEntity.ok().body(userService.getLoginToken(userLoginDto));
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
