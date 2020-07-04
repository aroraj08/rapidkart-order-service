package com.rapidkart.orderservice.service;

import com.rapidkart.orderservice.model.OrderDto;

public interface OrderService {

    Long createOrder(OrderDto orderDto);
}
