package com.example.msvc_purchases.mapper;

import com.example.msvc_purchases.dto.SupplierDTO;
import com.example.msvc_purchases.entity.Supplier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SupplierMapper {

    public SupplierDTO toDTO(Supplier supplier) {
        if (supplier == null) {
            return null;
        }

        return new SupplierDTO(
                supplier.getSupplierId(),
                supplier.getSupplierCode(),
                supplier.getCompanyName(),
                supplier.getEmail(),
                supplier.getPhone(),
                supplier.getAddress(),
                supplier.getStatus()
        );
    }

    public Supplier toEntity(SupplierDTO dto) {
        if (dto == null) {
            return null;
        }

        return Supplier.builder()
                .supplierId(dto.getSupplierId())
                .supplierCode(dto.getSupplierCode())
                .companyName(dto.getCompanyName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .status(dto.getStatus())
                .build();
    }

    public void updateEntityFromDTO(SupplierDTO dto, Supplier supplier) {
        if (dto == null || supplier == null) {
            return;
        }

        supplier.setSupplierCode(dto.getSupplierCode());
        supplier.setCompanyName(dto.getCompanyName());
        supplier.setEmail(dto.getEmail());
        supplier.setPhone(dto.getPhone());
        supplier.setAddress(dto.getAddress());
        supplier.setStatus(dto.getStatus());
    }

    public List<SupplierDTO> toDTOList(List<Supplier> suppliers) {
        if (suppliers == null) {
            return null;
        }

        return suppliers.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Supplier> toEntityList(List<SupplierDTO> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
