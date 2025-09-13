package com.example.msvc_products.controller;

import com.example.msvc_products.dto.BatchDTO;
import com.example.msvc_products.entity.Batch;
import com.example.msvc_products.entity.Product;
import com.example.msvc_products.mapper.BatchMapper;
import com.example.msvc_products.service.BatchService;
import com.example.msvc_products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/batches")
public class BatchController {

    @Autowired
    BatchService batchService;

    @Autowired
    ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<BatchDTO> create(@Validated @RequestBody BatchDTO batchDTO) {
        if (batchDTO.getProductCode() == null) {
            throw new RuntimeException("Product code must not be null");
        }
        String productCode = batchDTO.getProductCode();
        Product product = productService.getByProductCode(productCode);

        Batch batch = BatchMapper.toEntity(batchDTO, product);

        Batch saved = batchService.create(batch);

        return ResponseEntity.ok(BatchMapper.toDTO(saved));
    }

    @GetMapping("/by-purchase/{purchaseOrderId}/product/{productCode}")
    public ResponseEntity<BatchDTO> getBatchByPurchaseOrderAndProduct(
            @PathVariable Long purchaseOrderId,
            @PathVariable String productCode) {

        Batch batch = batchService.getBatchByPurchaseOrderAndProduct(purchaseOrderId, productCode);
        return ResponseEntity.ok(BatchMapper.toDTO(batch));
    }

}
