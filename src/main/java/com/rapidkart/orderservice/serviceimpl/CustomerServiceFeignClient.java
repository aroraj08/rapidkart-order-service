package com.rapidkart.orderservice.serviceimpl;

import com.rapidkart.model.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="rapidkart-customer-service")
public interface CustomerServiceFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = CustomerServiceImpl.CUSTOMER_INFO_PATH)
    ResponseEntity<CustomerDto> getCustomerInfo(@PathVariable Long customerId);
}
