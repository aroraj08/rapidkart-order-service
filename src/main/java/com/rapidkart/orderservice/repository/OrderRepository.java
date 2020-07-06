package com.rapidkart.orderservice.repository;

import com.rapidkart.orderservice.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    Page<Order> findAllByCustomerId(Long customerId, Pageable pageable);
}
