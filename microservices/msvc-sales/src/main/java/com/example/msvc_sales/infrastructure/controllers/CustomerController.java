package com.example.msvc_sales.infrastructure.controllers;

import com.example.msvc_sales.application.dtos.request.CustomerRequestDTO;
import com.example.msvc_sales.application.dtos.response.CustomerResponseDTO;
import com.example.msvc_sales.application.mapper.CustomerMapper;
import com.example.msvc_sales.application.services.CustomerService;
import com.example.msvc_sales.domain.models.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper mapper;

    @PostMapping("/create")
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerRequestDTO requestDTO) {
        Customer customer = mapper.toDomain(requestDTO);
        Customer savedCustomer = customerService.createCustomer(customer);
        System.out.println(savedCustomer.getCreatedAt()+" "+savedCustomer.getUpdatedAt());
        CustomerResponseDTO responseDTO = mapper.toResponseDTO(savedCustomer);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{ciCustomer}")
    public ResponseEntity<CustomerResponseDTO> getCustomer(@PathVariable String ciCustomer) {
        Customer customer = customerService.getCustomerById(ciCustomer);
        CustomerResponseDTO responseDTO = mapper.toResponseDTO(customer);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        List<CustomerResponseDTO> responseDTOs = mapper.toResponseDTOList(customers);

        return ResponseEntity.ok(responseDTOs);
    }

    @PutMapping("/{ciCustomer}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(
            @PathVariable String ciCustomer,
            @RequestBody CustomerRequestDTO requestDTO) {

        Customer customer = mapper.toDomain(requestDTO);
        Customer updatedCustomer = customerService.updateCustomer(ciCustomer, customer);
        CustomerResponseDTO responseDTO = mapper.toResponseDTO(updatedCustomer);

        return ResponseEntity.ok(responseDTO);
    }

    /*@DeleteMapping("/{ciCustomer}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String ciCustomer) {
        boolean deleted = customerService.deleteCustomer(ciCustomer);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }*/
    @DeleteMapping("/{ciCustomer}")
    public ResponseEntity<Map<String, Object>> deleteCustomer(@PathVariable String ciCustomer) {
        boolean deleted = customerService.deleteCustomer(ciCustomer);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Customer deleted successfully");
        response.put("customerId", ciCustomer);
        response.put("status", HttpStatus.OK.value());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{ciCustomer}/activate")
    public ResponseEntity<CustomerResponseDTO> activateCustomer(@PathVariable String ciCustomer) {
        Customer customer = customerService.activateCustomer(ciCustomer);
        CustomerResponseDTO responseDTO = mapper.toResponseDTO(customer);
        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping("/{ciCustomer}/deactivate")
    public ResponseEntity<CustomerResponseDTO> deactivateCustomer(@PathVariable String ciCustomer) {
        Customer customer = customerService.deactivateCustomer(ciCustomer);
        CustomerResponseDTO responseDTO = mapper.toResponseDTO(customer);
        return ResponseEntity.ok(responseDTO);
    }

}
