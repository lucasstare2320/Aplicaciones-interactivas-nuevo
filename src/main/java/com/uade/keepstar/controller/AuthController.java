package com.uade.keepstar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.keepstar.entity.dto.LoginRequest;
import com.uade.keepstar.entity.dto.UserRequest;
import com.uade.keepstar.entity.dto.UserResponse;
import com.uade.keepstar.exceptions.UnauthorizedException;
import com.uade.keepstar.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("register")
    public UserResponse registerUser(@RequestBody UserRequest request) {
        return userService.registerUser(request);
    }

    @PostMapping("login")
    public UserResponse login(@RequestBody LoginRequest request) throws UnauthorizedException {
        return userService.login(request);
    }
}
