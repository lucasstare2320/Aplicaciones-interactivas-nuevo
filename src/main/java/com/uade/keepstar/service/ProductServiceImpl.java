package com.uade.keepstar.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public ProductResponse crearProducto(ProductRequest request)
            throws CategoryNotFoundException, UserNotFoundException {
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
                .category(category.get()).build());
        return new ProductResponse(product);
    }

    @Override
        public ProductResponse actualizarProducto(Long id, ProductRequest request)
            throws CategoryNotFoundException, UserNotFoundException, ProductNotFoundException {

        Product existing = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        // Resolver relaciones (solo si existen)
        Category newCategory = categoryRepository.findByName(request.getCategory())
                .orElseThrow(CategoryNotFoundException::new);

        User newSeller = userRepository.findById(request.getUser_id())
                .orElseThrow(UserNotFoundException::new);

        boolean changed = false;

        // name
        if (!Objects.equals(existing.getName(), request.getName())) {
            existing.setName(request.getName());
            changed = true;
        }

        // description
        if (!Objects.equals(existing.getDescription(), request.getDescription())) {
            existing.setDescription(request.getDescription());
            changed = true;
        }

        // price (float): usar Float.compare para evitar problemas de precisión
        if (Float.compare(existing.getPrice(), request.getPrice()) != 0) {
            existing.setPrice(request.getPrice());
            changed = true;
        }

        // stock
        if (existing.getStock() != request.getStock()) {
            existing.setStock(request.getStock());
            changed = true;
        }

        // category (comparar por id si existe)
        if (existing.getCategory() == null ||
            !Objects.equals(existing.getCategory().getId(), newCategory.getId())) {
            existing.setCategory(newCategory);
            changed = true;
        }

        // seller (comparar por id)
        if (existing.getSeller() == null ||
            !Objects.equals(existing.getSeller().getId(), newSeller.getId())) {
            existing.setSeller(newSeller);
            changed = true;
        }

        // Nota: no tocamos 'active' porque no está en el Request

        // Guardar solo si hubo cambios (si no, devolvemos el actual sin persistir)
        Product saved = changed ? productRepository.save(existing) : existing;

        return new ProductResponse(saved);
    }
}
