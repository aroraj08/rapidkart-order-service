package com.rapidkart.orderservice.service;

import com.rapidkart.orderservice.exceptions.CustomerNotFoundException;
import com.rapidkart.orderservice.model.OrderDto;

public interface OrderService {

    Long createOrder(Long customerId, OrderDto orderDto) throws CustomerNotFoundException;
}
