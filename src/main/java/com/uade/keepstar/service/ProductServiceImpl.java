package com.uade.keepstar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.keepstar.entity.Category;
import com.uade.keepstar.entity.Product;
import com.uade.keepstar.entity.User;
import com.uade.keepstar.entity.dto.ProductRequest;
import com.uade.keepstar.entity.dto.ProductResponse;
import com.uade.keepstar.exceptions.CategoryNotFoundException;
import com.uade.keepstar.exceptions.UserNotFoundException;
import com.uade.keepstar.repository.CategoryRepository;
import com.uade.keepstar.repository.ProductRepository;
import com.uade.keepstar.repository.UserRepository;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<ProductResponse> getProducts() {

        return productRepository.findAll().stream().map(product -> new ProductResponse(product)).toList();
    }

    @Override
    public ProductResponse getIDList(Long id) {

        Optional<Product> product = productRepository.findById(id);
        return new ProductResponse(product.get());
    }

    @Override
    public ProductResponse crearProducto(ProductRequest request) throws CategoryNotFoundException, UserNotFoundException {
        Optional<Category> category = categoryRepository.findByName(request.getCategory());
            if (!category.isPresent()) {
                    throw new CategoryNotFoundException();
                }
        Optional<User> user = userRepository.findById(request.getUser_id()); 
        if (!user.isPresent()) {
            throw new UserNotFoundException();
        }

        Product product = productRepository.save(Product.builder()
        .name(request.getName())
        .description(request.getDescription())
        .price(request.getPrice())
        .stock(request.getStock())
        .seller(user.get())
        .category(category.get()).build()
        );
        return new ProductResponse(product);
    }

}
