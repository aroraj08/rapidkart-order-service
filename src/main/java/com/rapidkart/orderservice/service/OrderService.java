package com.rapidkart.orderservice.service;

import com.rapidkart.orderservice.exceptions.CustomerNotFoundException;
import com.rapidkart.orderservice.model.OrderDto;
import com.rapidkart.orderservice.model.OrderPagedList;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    Long createOrder(Long customerId, OrderDto orderDto) throws CustomerNotFoundException;

    OrderPagedList listOrders(Long customerId, Pageable pageable) throws CustomerNotFoundException;
}
