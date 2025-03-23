package com.BD.Demo.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.BD.Demo.dto.request.SaleRequestDTO;
import com.BD.Demo.dto.response.ProductResponseDTO;
import com.BD.Demo.dto.response.SaleResponseDTO;

import com.BD.Demo.entities.Sale;
import com.BD.Demo.services.SaleService;

@RestController
@RequestMapping("/api/sale")
@Tag(name = "Sale", description = "Sale management APIs")
public class SaleController {
    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public ResponseEntity<?> salesList() {
        return ResponseEntity.ok(saleService.findAllSales());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findSale(@PathVariable Long id) {
        Optional<Sale> saleOptional = saleService.findById(id);
        if(saleOptional.isPresent()){
            return ResponseEntity.ok(saleOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<SaleResponseDTO> createSale(@RequestBody SaleRequestDTO saleRequest) {
        SaleResponseDTO saleResponse = saleService.create(saleRequest);
        return new ResponseEntity<>(saleResponse, HttpStatus.CREATED);
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<?> updateSale(@PathVariable Long id, @RequestBody Sale updatedSale) {
        Optional<Sale> existingSale = saleService.findById(id);
        if (existingSale.isPresent()) {
            return ResponseEntity.ok(saleService.saveSale(updatedSale));
        }
        return ResponseEntity.notFound().build();
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSale(@PathVariable Long id) {
        Optional<Sale> existingSale = saleService.findById(id);
        if (existingSale.isPresent()) {
            saleService.deleteSale(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get top selling products", description = "Returns a list of top selling products")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved top selling products")
    @GetMapping("/top-selling-products")
    public List<ProductResponseDTO> getTopSellingProducts(
            @Parameter(description = "Number of top products to retrieve") 
            @RequestParam int topX) {
        return saleService.getTopSellingProducts(topX);
    }

    @Operation(summary = "Calculate profit", description = "Calculate total profit between dates")
    @ApiResponse(responseCode = "200", description = "Successfully calculated profit")
    @GetMapping("/calculateProfit")
    public ResponseEntity<Map<String, Object>> getProfitDetails(
            @Parameter(description = "Start date (YYYY-MM-DD)") @RequestParam LocalDate startDate,
            @Parameter(description = "End date (YYYY-MM-DD)") @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(saleService.calculateTotalProfit(startDate, endDate));
    }

}
