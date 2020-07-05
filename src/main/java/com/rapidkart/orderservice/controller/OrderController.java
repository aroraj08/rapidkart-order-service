package com.rapidkart.orderservice.controller;

import com.rapidkart.orderservice.exceptions.CustomerNotFoundException;
import com.rapidkart.orderservice.model.OrderDto;
import com.rapidkart.orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private static final String BASE_MAPPING = "/order/api/v1";

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{customerId}")
    public ResponseEntity createOrder(@PathVariable(name="customerId") Long customerId,
                                      @RequestBody @Valid OrderDto orderDto)
            throws CustomerNotFoundException {

        // validate order
        Long orderId = orderService.createOrder(customerId, orderDto);
        return ResponseEntity.created(URI.create(BASE_MAPPING + "/" + orderId.longValue()))
                .build();
    }

}
