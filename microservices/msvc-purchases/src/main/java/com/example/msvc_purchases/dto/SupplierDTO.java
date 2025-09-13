package com.example.msvc_purchases.dto;

import com.example.msvc_purchases.utils.SupplierStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDTO {

    private Long supplierId;
    private String supplierCode;
    private String companyName;
    private String email;
    private String phone;
    private String address;
    private SupplierStatus status = SupplierStatus.ACTIVE;
}
