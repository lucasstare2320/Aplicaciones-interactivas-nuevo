package com.uade.keepstar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.uade.keepstar.entity.dto.UserRequest;
import com.uade.keepstar.entity.dto.UserResponse;
import com.uade.keepstar.exceptions.UserNotFoundException;
import com.uade.keepstar.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserResponse> getUsers() {
        return userService.getUsers();
    }

    // (Opcional) obtener uno por id
    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) throws UserNotFoundException {
        return userService.getUserById(id);
    }

    @PatchMapping("/{id}")
    public UserResponse actualizarUser(@PathVariable Long id,
                                       @RequestBody UserRequest request)
            throws UserNotFoundException {
        return userService.actualizarUser(id, request);
    }
}
