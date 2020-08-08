package com.rapidkart.orderservice.serviceimpl;

import com.rapidkart.orderservice.domain.Order;

public interface OrderManager {

    Order newOrder(Order order);

    void processValidationResult(Long orderId, boolean isLegitimateCustomer);
}
