package com.uade.keepstar.service;

import com.uade.keepstar.entity.dto.LoginRequest;
import com.uade.keepstar.entity.dto.UserRequest;
import com.uade.keepstar.entity.dto.UserResponse;
import com.uade.keepstar.exceptions.UnauthorizedException;

public interface UserService {
    public UserResponse registerUser(UserRequest request);
    public UserResponse login(LoginRequest request) throws UnauthorizedException;
}
