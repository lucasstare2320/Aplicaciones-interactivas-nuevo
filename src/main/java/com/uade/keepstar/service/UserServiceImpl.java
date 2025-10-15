package com.uade.keepstar.service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.uade.keepstar.exceptions.UserNotFoundException;
import com.uade.keepstar.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    private void validateUserRequest(UserRequest request) {
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
    }

    @Override
    public UserResponse registerUser(UserRequest request) {
                
        validateUserRequest(request);
        
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

    @Override
    public List<UserResponse> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return new UserResponse(user);
    }


    @Override
    @Transactional
    public UserResponse actualizarUser(Long id, UserRequest request) throws UserNotFoundException {
        User existing = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        boolean changed = false;

        if (request.getUsername() != null && !Objects.equals(existing.getUsername(), request.getUsername())) {
            existing.setUsername(request.getUsername());
            changed = true;
        }
        if (request.getEmail() != null && !Objects.equals(existing.getEmail(), request.getEmail())) {
            existing.setEmail(request.getEmail());
            changed = true;
        }
        if (request.getFirstName() != null && !Objects.equals(existing.getFirstName(), request.getFirstName())) {
            existing.setFirstName(request.getFirstName());
            changed = true;
        }
        if (request.getLastName() != null && !Objects.equals(existing.getLastName(), request.getLastName())) {
            existing.setLastName(request.getLastName());
            changed = true;
        }
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            existing.setPassword(request.getPassword());
            changed = true;
        }

        if (changed) {
            userRepository.save(existing);
        }
        return new UserResponse(existing);
    }
}