package com.rapidkart.orderservice.serviceimpl;

import com.rapidkart.orderservice.domain.Order;
import com.rapidkart.orderservice.domain.OrderLine;
import com.rapidkart.orderservice.domain.OrderState;
import com.rapidkart.orderservice.domain.PaymentMode;
import com.rapidkart.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderManagerImplTestIT {

    @Autowired
    private OrderManager orderManager;

    private Order order;

    @BeforeEach
    void setUp() {

        Set<OrderLine> orderLineSet =
                new HashSet<>();

         orderLineSet.add(OrderLine.builder()
                    .itemId(12l)
                    .quantity(2)
                    .pricePerItem(50.0)
                    .build());

        order = Order.builder()
                .customerId(1l)
                .paymentMode(PaymentMode.CARD)
                .totalAmount(100.0)
                .orderLines(orderLineSet)
                .build();

    }

    @Test
    void newOrder() {

        Order savedOrder = orderManager.newOrder(order);
        assertNotNull(savedOrder);
        assertEquals(OrderState.VALIDATED, savedOrder.getOrderState());
    }
}