package com.rapidkart.orderservice.serviceimpl;

import com.rapidkart.orderservice.domain.Order;
import com.rapidkart.orderservice.domain.OrderEvent;
import com.rapidkart.orderservice.domain.OrderState;
import com.rapidkart.orderservice.repository.OrderRepository;
import com.rapidkart.orderservice.sm.OrderStateChangeInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderManagerImpl implements OrderManager{

    private final OrderRepository orderRepository;
    private final StateMachineFactory<OrderState, OrderEvent> stateMachineFactory;
    private final OrderStateChangeInterceptor orderStateChangeInterceptor;

    public static final String ORDER_ID_HEADER = "ORDER_ID_HEADER";

    @Override
    @Transactional
    public Order newOrder(Order order) {

        order.setId(null);
        order.setOrderState(OrderState.NEW);

        Order savedOrder = orderRepository.saveAndFlush(order);
        sendOrderEvent(savedOrder, OrderEvent.VALIDATE_ORDER);
        return savedOrder;
    }

    @Override
    public void processValidationResult(Long orderId, boolean isLegitimateCustomer) {

        Optional<Order> orderOptional = orderRepository.findById(orderId);
        orderOptional.ifPresentOrElse(order -> {

         if (isLegitimateCustomer) {
             sendOrderEvent(order, OrderEvent.VALIDATION_SUCCESS);
         } else {
             sendOrderEvent(order, OrderEvent.VALIDATION_FAILURE);
         }

        }, () -> log.error("Order does not exist for order id {}", orderId));
    }

    private void sendOrderEvent(Order savedOrder, OrderEvent event) {

        StateMachine<OrderState, OrderEvent> sm = build(savedOrder);
        // create a message object and send this object as an event
        Message msg = MessageBuilder.withPayload(event)
                        .setHeader(ORDER_ID_HEADER, savedOrder.getId().toString())
                        .build();

        sm.sendEvent(msg);
    }

    private StateMachine<OrderState,OrderEvent> build(Order savedOrder) {

        StateMachine<OrderState, OrderEvent> sm =
                stateMachineFactory.getStateMachine(String.valueOf(savedOrder.getId()));

        sm.stop();
        sm.getStateMachineAccessor()
                .doWithAllRegions(
                    sma -> {
                        sma.addStateMachineInterceptor(orderStateChangeInterceptor);
                        sma.resetStateMachine(new DefaultStateMachineContext<>(savedOrder.getOrderState(),
                                null, null, null ));
                    }
                );
        sm.start();
        return sm;
    }

}
