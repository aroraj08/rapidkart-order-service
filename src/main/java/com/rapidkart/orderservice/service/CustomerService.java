package com.rapidkart.orderservice.service;

import com.rapidkart.orderservice.model.CustomerDto;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    ResponseEntity<CustomerDto> getCustomerInfo(Long customerId);
}
