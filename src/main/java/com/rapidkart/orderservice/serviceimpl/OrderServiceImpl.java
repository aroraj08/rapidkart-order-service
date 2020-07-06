package com.rapidkart.orderservice.serviceimpl;

import com.rapidkart.orderservice.domain.Order;
import com.rapidkart.orderservice.exceptions.CustomerNotFoundException;
import com.rapidkart.orderservice.mapper.OrderMapper;
import com.rapidkart.orderservice.model.CustomerDto;
import com.rapidkart.orderservice.model.OrderDto;
import com.rapidkart.orderservice.model.OrderPagedList;
import com.rapidkart.orderservice.repository.OrderRepository;
import com.rapidkart.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
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
    private final RestTemplate restTemplate;

    @Value("${GET_CUSTOMER_SERVICE_URI}")
    private String CUSTOMER_SERVICE_URI;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper,
                            RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.restTemplate = restTemplate;
    }
    @Override
    public Long createOrder(Long customerId, OrderDto orderDto) throws CustomerNotFoundException {

        // check if customer exists in system
        if (isCustomerPresent(customerId)) {
            orderDto.setCustomerId(customerId);
            Order order = orderMapper.orderDtoToOrder(orderDto);

            order.getOrderLines().forEach(orderLine -> orderLine.setOrder(order));
            Order savedOrder = orderRepository.save(order);
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

    private ResponseEntity<CustomerDto> getCustomerData(Long customerId) {
        return restTemplate.getForEntity(CUSTOMER_SERVICE_URI + "/{customerId}",
                CustomerDto.class, customerId);
    }
    private boolean isCustomerPresent(Long customerId) throws CustomerNotFoundException {

        ResponseEntity<CustomerDto> responseEntity = getCustomerData(customerId);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            return false;
        }
            return true;
    }
}
