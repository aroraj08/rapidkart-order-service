package com.rapidkart.orderservice.repository;

import com.rapidkart.orderservice.domain.Order;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {


}
