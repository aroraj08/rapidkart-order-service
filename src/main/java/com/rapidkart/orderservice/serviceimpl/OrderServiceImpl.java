package com.rapidkart.orderservice.serviceimpl;

import com.rapidkart.orderservice.domain.Order;
import com.rapidkart.orderservice.exceptions.CustomerNotFoundException;
import com.rapidkart.orderservice.mapper.OrderMapper;
import com.rapidkart.model.CustomerDto;
import com.rapidkart.model.OrderDto;
import com.rapidkart.model.OrderPagedList;
import com.rapidkart.orderservice.repository.OrderRepository;
import com.rapidkart.orderservice.service.CustomerService;
import com.rapidkart.orderservice.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CustomerService customerService;
    private final OrderManager orderManager;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper,
                            RestTemplate restTemplate, CustomerService customerService,
                            OrderManager orderManager) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.customerService = customerService;
        this.orderManager = orderManager;
    }

    @Override
    public Long createOrder(Long customerId, OrderDto orderDto) throws CustomerNotFoundException {

        // check if customer exists in system
        if (isCustomerPresent(customerId)) {
            orderDto.setCustomerId(customerId);
            Order order = orderMapper.orderDtoToOrder(orderDto);

            order.getOrderLines().forEach(orderLine -> orderLine.setOrder(order));
            //Order savedOrder = orderRepository.save(order);
            Order savedOrder = orderManager.newOrder(order);
            return savedOrder.getId();
        } else {
            throw new CustomerNotFoundException("Customer does not exist");
        }
    }

    @Override
    public OrderPagedList listOrders(Long customerId, Pageable pageable)
            throws CustomerNotFoundException {

        if (isCustomerPresent(customerId)) {
            Page<Order> orderPage = orderRepository.findAllByCustomerId(customerId, pageable);

            return new OrderPagedList(orderPage.stream()
                 .map(orderMapper :: orderToOrderDto)
                    .collect(Collectors.toList()), PageRequest.of(
                    orderPage.getPageable().getPageNumber(),
                    orderPage.getPageable().getPageSize()),
                    orderPage.getTotalElements());

        } else {
            throw new CustomerNotFoundException("Customer does not exist");
        }
    }

    private boolean isCustomerPresent(Long customerId) throws CustomerNotFoundException {

        ResponseEntity<CustomerDto> responseEntity = customerService.getCustomerInfo(customerId);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            return false;
        }
            return true;
    }
}
