package com.uade.keepstar.service;

import org.springframework.stereotype.Service;

import com.uade.keepstar.entity.Image;
import com.uade.keepstar.exceptions.ProductNotFoundException;

@Service
public interface ImageService {
    public Image create(Image image, long productId) throws ProductNotFoundException;

    public Image viewById(long id);

}