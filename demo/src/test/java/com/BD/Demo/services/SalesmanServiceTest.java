package com.BD.Demo.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.BD.Demo.entities.Salesman;
import com.BD.Demo.repositories.SalesmanRepository;

@ExtendWith(MockitoExtension.class)
class SalesmanServiceTest {

    @Mock
    private SalesmanRepository salesmanRepository;

    @InjectMocks
    private SalesmanService salesmanService;

    private Salesman salesman;

    @BeforeEach
    void setUp() {
        salesman = Salesman.builder()
                .ci("12345678")
                .fullName("John Doe")
                .birthday(LocalDate.of(1990, 1, 1))
                .phoneNumber("123-456-789")
                .state(true)
                .build();
    }

    @Test
    void findAll_ShouldReturnListOfSalesmen() {
        // Arrange
        when(salesmanRepository.findAll()).thenReturn(Arrays.asList(salesman));

        // Act
        List<Salesman> result = salesmanService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("12345678", result.get(0).getCi());
    }

    @Test
    void findById_ShouldReturnSalesman() {
        // Arrange
        when(salesmanRepository.findById("12345678")).thenReturn(Optional.of(salesman));

        // Act
        Optional<Salesman> result = salesmanService.findById("12345678");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getFullName());
    }

    @Test
    void saveSalesman_ShouldReturnSavedSalesman() {
        // Arrange
        when(salesmanRepository.save(any(Salesman.class))).thenReturn(salesman);

        // Act
        Salesman result = salesmanService.saveSalesman(salesman);

        // Assert
        assertNotNull(result);
        assertEquals("12345678", result.getCi());
        assertEquals("John Doe", result.getFullName());
    }

    @Test
    void deleteSalesman_ShouldCallRepository() {
        // Arrange
        String ci = "12345678";

        // Act
        salesmanService.deleteSalesman(ci);

        // Assert
        verify(salesmanRepository, times(1)).deleteById(ci);
    }

    @Test
    void updateSalesman_ShouldReturnUpdatedSalesman() {
        // Arrange
        String ci = "12345678";
        Salesman updatedSalesman = Salesman.builder()
                .ci(ci)
                .fullName("John Updated")
                .birthday(LocalDate.of(1990, 1, 1))
                .phoneNumber("987-654-321")
                .state(true)
                .build();

        when(salesmanRepository.findById(ci)).thenReturn(Optional.of(salesman));
        when(salesmanRepository.save(any(Salesman.class))).thenReturn(updatedSalesman);

        // Act
        Optional<Salesman> result = salesmanService.updateSalesman(ci, updatedSalesman);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("John Updated", result.get().getFullName());
        assertEquals("987-654-321", result.get().getPhoneNumber());
    }
}