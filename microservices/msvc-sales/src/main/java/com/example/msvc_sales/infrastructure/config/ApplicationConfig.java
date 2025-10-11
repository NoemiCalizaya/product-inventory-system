package com.example.msvc_sales.infrastructure.config;

import com.example.msvc_sales.application.services.CustomerService;
import com.example.msvc_sales.application.usecases.CreateCustomerUseCaseImpl;
import com.example.msvc_sales.application.usecases.DeleteCustomerUseCaseImpl;
import com.example.msvc_sales.application.usecases.RetrieveCustomerUseCaseImpl;
import com.example.msvc_sales.application.usecases.UpdateCustomerUseCaseImpl;
import com.example.msvc_sales.domain.ports.in.CreateCustomerUseCase;
import com.example.msvc_sales.domain.ports.in.RetrieveCustomerUseCase;
import com.example.msvc_sales.domain.ports.out.CustomerRepositoryPort;
import com.example.msvc_sales.infrastructure.repositories.JpaCustomerRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public CustomerService customerService(CustomerRepositoryPort customerRepositoryPort) {
        return new CustomerService(
                new CreateCustomerUseCaseImpl(customerRepositoryPort),
                new RetrieveCustomerUseCaseImpl(customerRepositoryPort),
                new UpdateCustomerUseCaseImpl(customerRepositoryPort),
                new DeleteCustomerUseCaseImpl(customerRepositoryPort)
        );
    }

    @Bean
    public CustomerRepositoryPort customerRepositoryPort(JpaCustomerRepositoryAdapter customerJpaRepositoryAdapter) {
        return customerJpaRepositoryAdapter;
    }

}
