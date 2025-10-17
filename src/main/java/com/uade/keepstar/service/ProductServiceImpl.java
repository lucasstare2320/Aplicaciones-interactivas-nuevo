package com.uade.keepstar.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.keepstar.entity.Category;
import com.uade.keepstar.entity.Product;

import com.uade.keepstar.entity.dto.ProductRequest;
import com.uade.keepstar.entity.dto.ProductResponse;
import com.uade.keepstar.exceptions.CategoryNotFoundException;
import com.uade.keepstar.exceptions.DuplicateProductException;
import com.uade.keepstar.exceptions.ProductNotFoundException;
import com.uade.keepstar.exceptions.UserNotFoundException;
import com.uade.keepstar.repository.CategoryRepository;
import com.uade.keepstar.repository.ProductRepository;


import jakarta.transaction.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired private ProductRepository productRepository;
    @Autowired private CategoryRepository categoryRepository;


@Override
public List<ProductResponse> getProducts(Double minPrice, Double maxPrice, Long categoryId) throws ProductNotFoundException {
    boolean noFilters = (minPrice == null && maxPrice == null && categoryId == null);
    if (noFilters) {
        return productRepository.findByActiveTrue().stream().map(ProductResponse::of).toList();
        }
    return productRepository.findByOptionalFilters(minPrice, maxPrice, categoryId)
            .stream().map(ProductResponse::of).toList();
}
   
@Override
public ProductResponse getIDList(Long id) throws ProductNotFoundException {
    var product = productRepository.findByIdAndActiveTrue(id)
            .orElseThrow(ProductNotFoundException::new);
    return new ProductResponse(product);
}

@Override
@Transactional
public void deleteProduct(Long id) throws ProductNotFoundException {
    var product = productRepository.findById(id)
            .orElseThrow(ProductNotFoundException::new);
    if (!product.isActive()) return;    
    product.setActive(false);
    productRepository.save(product);
    
}

    @Override
    @Transactional
    public ProductResponse crearProducto(ProductRequest request)
            throws CategoryNotFoundException, UserNotFoundException, DuplicateProductException {

        Optional<Product > existing = productRepository.findByNameIgnoreCaseAndCategory_Id(request.getName(), request.getCategoryId());
        if (existing.isPresent()) {
            throw new DuplicateProductException();
        }
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        Product p = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .active(true)
                .category(category)
                .discount(request.getDiscount())
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

        if (request.getCategoryId() != null) {
            Category newCat = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(CategoryNotFoundException::new);
            if (existing.getCategory() == null ||
                !Objects.equals(existing.getCategory().getId(), newCat.getId())) {
                existing.setCategory(newCat);
                changed = true;
            }
        }
        Product saved = changed ? productRepository.save(existing) : existing;
        return new ProductResponse(saved);
    }
}
