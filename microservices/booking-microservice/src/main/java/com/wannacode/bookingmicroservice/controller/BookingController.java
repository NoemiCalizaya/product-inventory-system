package com.wannacode.bookingmicroservice.controller;

import com.wannacode.bookingmicroservice.client.StockClient;
import com.wannacode.bookingmicroservice.dto.OrderDTO;
import com.wannacode.bookingmicroservice.entity.Order;
import com.wannacode.bookingmicroservice.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockClient stockClient;

    @PostMapping("/order")
    //@CircuitBreaker(name = "stockService", fallbackMethod = "fallbackToStockService")
    public String saveOrder(@RequestBody OrderDTO orderDTO) {

        boolean inStock = orderDTO.getOrderItems().stream()
                .allMatch(orderItem -> stockClient.stockAvailable(orderItem.getCode()));

        if (inStock) {
            Order order = new Order();
            order.setOrderNo(UUID.randomUUID().toString());
            order.setOrderItems(orderDTO.getOrderItems());

            orderRepository.save(order);

            return "Order Saved";
        }

        return "Order Cannot be Saved";
    }

    // ⚠️ IMPORTANTE: el fallback debe tener la misma firma que el método original
    private String fallbackToStockService(OrderDTO orderDTO, Throwable throwable) {
        return "Stock service is unavailable, please try again later";
    }
}
