package com.uade.keepstar.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.keepstar.entity.Category;
import com.uade.keepstar.entity.Product;
import com.uade.keepstar.entity.User;
import com.uade.keepstar.entity.dto.ProductRequest;
import com.uade.keepstar.entity.dto.ProductResponse;
import com.uade.keepstar.exceptions.CategoryNotFoundException;
import com.uade.keepstar.exceptions.ProductNotFoundException;
import com.uade.keepstar.exceptions.UserNotFoundException;
import com.uade.keepstar.repository.CategoryRepository;
import com.uade.keepstar.repository.ProductRepository;
import com.uade.keepstar.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired private ProductRepository productRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private UserRepository userRepository;

@Override
public List<ProductResponse> getProducts(Double minPrice, Double maxPrice, Long categoryId) throws ProductNotFoundException {
    boolean noFilters = (minPrice == null && maxPrice == null && categoryId == null);
    if (noFilters) {

        List<ProductResponse > products =  productRepository.findAll().stream()
                .map(ProductResponse::of)
                .toList();
                if (products.isEmpty()) {
                    throw new ProductNotFoundException ();
                }
                return products;
    }

    if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
        double temp = minPrice;
        minPrice = maxPrice;
        maxPrice = temp;
    }

    List<ProductResponse> products =  productRepository.findByOptionalFilters(minPrice, maxPrice, categoryId).stream()
            .map(ProductResponse::of)
            .toList();
            if (products.isEmpty()) {
                throw new ProductNotFoundException ();
            }
            return products;
}


    @Override
    public ProductResponse getIDList(Long id) throws ProductNotFoundException {
        Product p = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        return new ProductResponse(p);
    }

    @Override
    @Transactional
    public ProductResponse crearProducto(ProductRequest request)
            throws CategoryNotFoundException, UserNotFoundException {

        // Validar Category por ID (solo elige entre existentes)
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        // Validar vendedor
        User seller = userRepository.findById(request.getSellerId())
                .orElseThrow(UserNotFoundException::new);

        Product p = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .active(true)
                .category(category)
                .seller(seller)
                .build();

        Product saved = productRepository.save(p);
        return new ProductResponse(saved);
    }

    @Override
    @Transactional
    public ProductResponse actualizarProducto(Long id, ProductRequest request)
            throws CategoryNotFoundException, UserNotFoundException, ProductNotFoundException {

        Product existing = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        boolean changed = false;

        // Campos simples
        if (request.getName() != null && !Objects.equals(existing.getName(), request.getName())) {
            existing.setName(request.getName());
            changed = true;
        }
        if (request.getDescription() != null
                && !Objects.equals(existing.getDescription(), request.getDescription())) {
            existing.setDescription(request.getDescription());
            changed = true;
        }
        if (request.getPrice() != 0 && existing.getPrice() != request.getPrice()) {
            existing.setPrice(request.getPrice());
            changed = true;
        }
        if (request.getStock() != 0 && existing.getStock() != request.getStock()) {
            existing.setStock(request.getStock());
            changed = true;
        }

        // Category (por ID)
        if (request.getCategoryId() != null) {
            Category newCat = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(CategoryNotFoundException::new);
            if (existing.getCategory() == null ||
                !Objects.equals(existing.getCategory().getId(), newCat.getId())) {
                existing.setCategory(newCat);
                changed = true;
            }
        }

        // Seller (por ID)
        if (request.getSellerId() != null) {
            User newSeller = userRepository.findById(request.getSellerId())
                    .orElseThrow(UserNotFoundException::new);
            if (existing.getSeller() == null ||
                !Objects.equals(existing.getSeller().getId(), newSeller.getId())) {
                existing.setSeller(newSeller);
                changed = true;
            }
        }

        Product saved = changed ? productRepository.save(existing) : existing;
        return new ProductResponse(saved);
    }
}
