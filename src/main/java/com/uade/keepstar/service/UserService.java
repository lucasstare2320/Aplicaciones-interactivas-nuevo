package com.uade.keepstar.service;

import java.util.List;

import com.uade.keepstar.entity.dto.LoginRequest;
import com.uade.keepstar.entity.dto.UserRequest;
import com.uade.keepstar.entity.dto.UserResponse;
import com.uade.keepstar.exceptions.UnauthorizedException;
import com.uade.keepstar.exceptions.UserNotFoundException;

public interface UserService {
    UserResponse registerUser(UserRequest request);
    UserResponse login(LoginRequest request) throws UnauthorizedException;
    UserResponse actualizarUser(Long id, UserRequest request) throws UserNotFoundException;
    List<UserResponse> getUsers();
    UserResponse getUserById(Long id) throws UserNotFoundException;
}
