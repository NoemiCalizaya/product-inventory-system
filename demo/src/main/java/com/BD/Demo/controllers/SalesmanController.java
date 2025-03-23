package com.BD.Demo.controllers;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BD.Demo.entities.Salesman;
import com.BD.Demo.services.SalesmanService;

@RestController
@RequestMapping("/api/salesman")
public class SalesmanController {
    
    private final SalesmanService salesmanService;

    public SalesmanController(SalesmanService salesmanService){
        this.salesmanService = salesmanService;
    }

    @GetMapping
    public ResponseEntity<?> salesmansList() {
        return ResponseEntity.ok(this.salesmanService.findAll());
    }

    @GetMapping("/{ci}")
    public ResponseEntity<?> findSalesman(@PathVariable String ci) {
        Optional<Salesman> salesmanOptional = salesmanService.findById(ci);
        if(salesmanOptional.isPresent()){
            return ResponseEntity.ok(salesmanOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createSalesman(@RequestBody Salesman salesman) {
        return ResponseEntity.ok(salesmanService.saveSalesman(salesman));
    }

    @PutMapping("/{ci}")
    public ResponseEntity<?> updateSalesman(@PathVariable String ci, @RequestBody Salesman updatedSalesman) {
        Optional<Salesman> existingSalesman = salesmanService.findById(ci);
        if (existingSalesman.isPresent()) {
            updatedSalesman.setCi(ci); // Asegurarnos mantener el mismo CI
            return ResponseEntity.ok(salesmanService.saveSalesman(updatedSalesman));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{ci}")
    public ResponseEntity<?> deleteSalesman(@PathVariable String ci) {
        Optional<Salesman> existingSalesman = salesmanService.findById(ci);
        if (existingSalesman.isPresent()) {
            salesmanService.deleteSalesman(ci);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
