package com.example.msvc_products.controller;

import com.example.msvc_products.dto.ProductDTO;
import com.example.msvc_products.entity.Category;
import com.example.msvc_products.entity.Product;
import com.example.msvc_products.mapper.CategoryMapper;
import com.example.msvc_products.mapper.ProductMapper;
import com.example.msvc_products.service.CategoryService;
import com.example.msvc_products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> save(@RequestBody ProductDTO productDTO) {
        Category category = categoryService.getCategoryId(productDTO.getCategoryId());
        Product product = ProductMapper.toEntity(productDTO, category);
        Product saved = productService.create(product);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ProductMapper.toDTO(saved));
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() {
        List<Product> products = productService.getAll();

        if(products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<ProductDTO> productDTOS = productService.getAll().stream()
                .map(ProductMapper::toDTO)
                .toList();

        return ResponseEntity.ok(productDTOS);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategoryId(@PathVariable Long id) {
        List<Product> products = productService.getProductsByCategoryId(id);

        if(products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<ProductDTO> productDTOS = productService.getProductsByCategoryId(id).stream()
                .map(ProductMapper::toDTO)
                .toList();

        return ResponseEntity.ok(productDTOS);
    }

    @GetMapping("/code/{productCode}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable String productCode) {
        Product product = productService.getByProductCode(productCode);

        ProductDTO productDTO = ProductMapper.toDTO(product);
        return ResponseEntity.ok(productDTO);

    }

    @PutMapping("/{productCode}")
    public ResponseEntity<ProductDTO> update(@PathVariable String productCode, @RequestBody ProductDTO newProductDTO) {
        Category category = categoryService.getCategoryId(newProductDTO.getCategoryId());
        Product newProduct = ProductMapper.toEntity(newProductDTO, category);

        Product updated = productService.update(productCode, newProduct);

        return ResponseEntity.ok(ProductMapper.toDTO(updated));
    }

    @DeleteMapping("/{productCode}")
    public ResponseEntity<Void> delete(@PathVariable String productCode) {
        productService.delete(productCode);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{code}/cost")
    public ResponseEntity<Void> updateCost(@PathVariable String code,
                                           @RequestParam BigDecimal unitCost) {
        productService.updateCost(code, unitCost);
        return ResponseEntity.noContent().build();
    }

}
