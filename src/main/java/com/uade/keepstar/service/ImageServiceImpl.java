package com.uade.keepstar.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.keepstar.entity.Image;
import com.uade.keepstar.entity.Product;

import com.uade.keepstar.exceptions.ProductNotFoundException;
import com.uade.keepstar.repository.ImageRepository;
import com.uade.keepstar.repository.ProductRepository;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Image create(Image image, long productId) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(productId);
        if (!product.isPresent()) {
            throw new ProductNotFoundException();                    
        }
        image.setProduct(product.get());
        return imageRepository.save(image);
    }

    @Override
    public Image viewById(long id) {
        return imageRepository.findById(id).get();
    }
}