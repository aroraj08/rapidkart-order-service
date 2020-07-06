package com.rapidkart.orderservice.controller;

import com.rapidkart.orderservice.exceptions.CustomerNotFoundException;
import com.rapidkart.orderservice.model.OrderDto;
import com.rapidkart.orderservice.model.OrderPagedList;
import com.rapidkart.orderservice.service.OrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.beans.support.PagedListHolder.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping("/api/v1/orders/{customerId}")
public class OrderController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;
    private static final String BASE_MAPPING = "/api/v1/orders/";

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity createOrder(@PathVariable("customerId") Long customerId,
                                      @RequestBody @Valid OrderDto orderDto)
            throws CustomerNotFoundException {

        // validate order
        Long orderId = orderService.createOrder(customerId, orderDto);
        return ResponseEntity.created(URI.create(BASE_MAPPING + customerId +
                "/" + orderId.longValue()))
                .build();
    }

    @GetMapping
    public OrderPagedList listOrders(@PathVariable("customerId") Long customerId,
                                     @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                     @RequestParam(value = "pageSize" , required = false) Integer pageSize) throws CustomerNotFoundException {

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        if (pageSize == null || pageSize < 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        //return orderService.listOrders(customerId, PageRequest.of(pageNumber, pageSize));
        return orderService.listOrders(customerId, PageRequest.of(pageNumber, pageSize,
                Sort.by(Sort.Direction.DESC, "createdDate")));
    }

}
