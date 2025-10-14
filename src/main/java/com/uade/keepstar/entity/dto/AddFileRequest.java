package com.uade.keepstar.entity.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AddFileRequest {
    private String name;
    private MultipartFile file;
    private long productId;
}
