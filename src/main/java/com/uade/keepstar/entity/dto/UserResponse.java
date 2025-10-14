package com.uade.keepstar.entity.dto;

import java.util.List;

import com.uade.keepstar.entity.Role;
import com.uade.keepstar.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private List<Role> roles;

    public UserResponse(User user) {
        id = user.getId();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();
        username = user.getUsername();
        roles = user.getRoles();
    }
}
