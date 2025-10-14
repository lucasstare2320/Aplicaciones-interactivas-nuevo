package com.uade.keepstar.service;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.uade.keepstar.entity.Role;
import com.uade.keepstar.entity.User;
import com.uade.keepstar.entity.dto.LoginRequest;
import com.uade.keepstar.entity.dto.UserRequest;
import com.uade.keepstar.entity.dto.UserResponse;
import com.uade.keepstar.exceptions.UnauthorizedException;
import com.uade.keepstar.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponse registerUser(UserRequest request) {
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email is mandatory");
        }
        if (request.getFirstName() == null || request.getFirstName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "firstName is mandatory");
        }
        if (request.getLastName() == null || request.getLastName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "lastName is mandatory");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password");
        }
        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username is mandatory");
        }
        Optional<User> optional = userRepository.findByEmail(request.getEmail());
        if (optional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email already exists");    
        }
        optional = userRepository.findByUsername(request.getUsername());
        if (optional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username already exists");    
        }
        User user = userRepository.save(User.builder()
            .email(request.getEmail())
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .password(request.getPassword())
            .username(request.getUsername())
            .roles(Arrays.asList(Role.CLIENT))
            .build()
        );
        return new UserResponse(user);
    }

    @Override
    public UserResponse login(LoginRequest request) throws UnauthorizedException {
        Optional<User> user = userRepository.findByUsername(request.getUsername());
        if (!user.isPresent() || !user.get().getPassword().equals(request.getPassword())) {
            throw new UnauthorizedException();
        }
        return new UserResponse(user.get());
    }


}
