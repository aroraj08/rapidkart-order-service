package com.rapidkart.orderservice.serviceimpl;

import com.rapidkart.model.CustomerDto;
import com.rapidkart.orderservice.service.CustomerService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Profile("local-discovery")
@Service
public class CustomerServiceFeignImpl implements CustomerService {

    private final CustomerServiceFeignClient customerServiceFeignClient;

    public CustomerServiceFeignImpl(CustomerServiceFeignClient customerServiceFeignClient) {
        this.customerServiceFeignClient = customerServiceFeignClient;
    }

    @Override
    public ResponseEntity<CustomerDto> getCustomerInfo(Long customerId) {

        ResponseEntity<CustomerDto>  responseEntity =
                customerServiceFeignClient.getCustomerInfo(customerId);

        return responseEntity;
    }
}
