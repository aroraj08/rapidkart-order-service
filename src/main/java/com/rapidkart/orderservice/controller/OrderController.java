package com.rapidkart.orderservice.controller;

import com.rapidkart.orderservice.model.OrderDto;
import com.rapidkart.orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/order/api/v1")
public class OrderController {

    private final OrderService orderService;
    private static final String BASE_MAPPING = "/order/api/v1";

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity createOrder(@RequestBody @Valid OrderDto orderDto) {

        // validate order
        Long orderId = orderService.createOrder(orderDto);
        return ResponseEntity.created(URI.create(BASE_MAPPING + "/" + orderId.longValue()))
                .build();
    }
}
