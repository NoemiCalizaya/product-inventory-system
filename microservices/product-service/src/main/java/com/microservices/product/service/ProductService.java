package com.microservices.product.service;

import com.microservices.product.dto.ProductDTO;
import com.microservices.product.model.Category;
import com.microservices.product.model.Product;
import com.microservices.product.repository.CategoryRepository;
import com.microservices.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProductDTO> getProductById(String productCod) {
        return productRepository.findById(productCod)
                .map(this::convertToDTO);
    }

    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategory_CategoryId(categoryId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsByState(String state) {
        return productRepository.findByState(state).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDTO createProduct(Product product) {
        Category category = categoryRepository.findById(product.getCategory().getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        product.setCategory(category);
        return convertToDTO(productRepository.save(product));
    }

    @Transactional
    public Optional<ProductDTO> updateProduct(String productCod, Product product) {
        return productRepository.findById(productCod)
                .map(existingProduct -> {
                    Category category = categoryRepository.findById(product.getCategory().getCategoryId())
                            .orElseThrow(() -> new EntityNotFoundException("Category not found"));
                    
                    existingProduct.setCategory(category);
                    existingProduct.setProductName(product.getProductName());
                    existingProduct.setSalePrice(product.getSalePrice());
                    existingProduct.setUnitMeasure(product.getUnitMeasure());
                    existingProduct.setProfitMargin(product.getProfitMargin());
                    existingProduct.setState(product.getState());
                    
                    return convertToDTO(productRepository.save(existingProduct));
                });
    }

    @Transactional
    public void deleteProduct(String productCod) {
        productRepository.deleteById(productCod);
    }

    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
            product.getProductCod(),
            product.getCategory().getCategoryId(),
            product.getCategory().getDescription(),
            product.getProductName(),
            product.getSalePrice(),
            product.getUnitMeasure(),
            product.getProfitMargin(),
            product.getState()
        );
    }
}