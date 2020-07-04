package com.rapidkart.orderservice.serviceimpl;

import com.rapidkart.orderservice.domain.Order;
import com.rapidkart.orderservice.mapper.OrderMapper;
import com.rapidkart.orderservice.model.OrderDto;
import com.rapidkart.orderservice.repository.OrderRepository;
import com.rapidkart.orderservice.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }
    @Override
    public Long createOrder(OrderDto orderDto) {
        Order order = orderMapper.orderDtoToOrder(orderDto);
        Order savedOrder = orderRepository.save(order);
        return savedOrder.getId();
    }
}
