package com.rapidkart.orderservice.serviceimpl;

import com.rapidkart.orderservice.model.CustomerDto;
import com.rapidkart.orderservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Profile("!local-discovery")
@Service
public class CustomerServiceImpl implements CustomerService {

    @Value("${CUSTOMER_SERVICE_HOST}")
    private String CUSTOMER_SERVICE_HOST;

    public static final String CUSTOMER_INFO_PATH = "/api/v1/customers/{customerId}";
    private final RestTemplate restTemplate;

    public CustomerServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<CustomerDto> getCustomerInfo(Long customerId) {
        return restTemplate.getForEntity(CUSTOMER_SERVICE_HOST + CUSTOMER_INFO_PATH,
                CustomerDto.class, customerId);
    }
}
