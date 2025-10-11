package com.example.msvc_sales.application.dtos;

import com.example.msvc_sales.infrastructure.controllers.entities.utils.PaymentStatus;
import com.example.msvc_sales.infrastructure.controllers.entities.utils.SalesOrderStatus;
import com.example.msvc_sales.infrastructure.entities.CustomerEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SalesOrderDTO {

    private Long salesOrderId;
    private String orderNumber;
    private CustomerEntity customer;
    private LocalDate orderDate;
    private LocalDate requiredDate;
    private LocalDate shippedDate;
    private SalesOrderStatus status = SalesOrderStatus.DRAFT;
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;
    private BigDecimal subtotal = BigDecimal.ZERO;
    private BigDecimal discountAmount = BigDecimal.ZERO;
    private BigDecimal taxAmount = BigDecimal.ZERO;
    private BigDecimal totalAmount = BigDecimal.ZERO;
}
