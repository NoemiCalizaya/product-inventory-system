package com.example.msvc_sales.domain.models;

import com.example.msvc_sales.domain.valueobject.CustomerStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    private String ciCustomer;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String email;
    private String phone;
    private String address;
    private CustomerStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getAge() {
        if (birthday != null) {
            return LocalDate.now().getYear() - birthday.getYear();
        }
        return 0;
    }

    public boolean isActive() {
        return status == CustomerStatus.ACTIVE;
    }

    public boolean isAdult() {
        return getAge() >= 18;
    }

    public void activate() {
        this.status = CustomerStatus.ACTIVE;
    }

    public void deactivate() {
        this.status = CustomerStatus.INACTIVE;
    }

    public void validateForCreation() {
        if (!isAdult()) {
            throw new IllegalArgumentException("Customer must be at least 18 years old");
        }
        if (ciCustomer == null || ciCustomer.isBlank()) {
            throw new IllegalArgumentException("Customer ID is required");
        }
    }
}
