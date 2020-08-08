package com.rapidkart.orderservice.sm.action;

import com.rapidkart.orderservice.config.JmsConfig;
import com.rapidkart.orderservice.domain.Order;
import com.rapidkart.orderservice.domain.OrderEvent;
import com.rapidkart.orderservice.domain.OrderState;
import com.rapidkart.orderservice.repository.OrderRepository;
import com.rapidkart.orderservice.serviceimpl.OrderManagerImpl;
import com.rapidkart.model.events.ValidateOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidateOrderAction implements Action<OrderState, OrderEvent> {

    private final OrderRepository orderRepository;
    private final JmsTemplate jmsTemplate;

    @Override
    public void execute(StateContext<OrderState, OrderEvent> context) {
        String orderId = (String) context.getMessage().getHeaders().get(OrderManagerImpl.ORDER_ID_HEADER);
        Optional<Order> order = orderRepository.findById(Long.valueOf(orderId));
        order.ifPresentOrElse(o -> {
            Long customerId = o.getCustomerId();
            jmsTemplate.convertAndSend(JmsConfig.CUSTOMER_VALIDATION_QUEUE,
                    ValidateOrderRequest.builder()
                            .customerId(customerId)
                            .orderId(o.getId())
                            .build());
        }, () -> log.error("Order Id not found in Database"));

    }
}
