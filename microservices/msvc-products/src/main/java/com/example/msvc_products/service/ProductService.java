package com.example.msvc_products.service;

import com.example.msvc_products.entity.Category;
import com.example.msvc_products.entity.Product;
import com.example.msvc_products.repository.CategoryRepository;
import com.example.msvc_products.repository.ProductRepository;
import com.example.msvc_products.utils.CategoryStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService{

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public Product create(Product product) {
        String name = product.getCategory().getName();

        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setCategory(category);

        return productRepository.save(product);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByCategoryId(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found");
        }

        return productRepository.findByCategory_Id(id);
    }

    public Product getByProductCode(String productCode) {

        return productRepository.findById(productCode).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product update(String productCode, Product newProduct) {

        return productRepository.findById(productCode)
                .map(product -> {
                    newProduct.setProductCode(product.getProductCode());
                    return productRepository.save(newProduct);
                }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void delete(String productCode) {
        Product product = productRepository.findById(productCode)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.delete(product);
    }

    public void updateCost(String productCode, BigDecimal unitCost) {
        Product product = productRepository.findById(productCode)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setUnitCost(unitCost);
        productRepository.save(product);
    }
}
