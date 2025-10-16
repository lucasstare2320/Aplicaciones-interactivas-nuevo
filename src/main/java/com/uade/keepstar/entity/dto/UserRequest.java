package com.uade.keepstar.entity.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
}   
