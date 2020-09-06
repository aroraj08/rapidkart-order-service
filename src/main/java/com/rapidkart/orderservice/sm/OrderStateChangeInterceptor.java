package com.rapidkart.orderservice.sm;

import com.rapidkart.orderservice.domain.Order;
import com.rapidkart.orderservice.domain.OrderEvent;
import com.rapidkart.orderservice.domain.OrderState;
import com.rapidkart.orderservice.repository.OrderRepository;
import com.rapidkart.orderservice.serviceimpl.OrderManagerImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderStateChangeInterceptor extends StateMachineInterceptorAdapter<OrderState, OrderEvent> {

    private final OrderRepository orderRepository;

    /**
     * This method will be used to change state in persistent store from source to target.
     * StateMachine will have correct state. THis interceptor is responsible for changing state in DB
     * @param state
     * @param message
     * @param transition
     * @param stateMachine
     */
    @Override
    @Transactional
    public void preStateChange(State<OrderState, OrderEvent> state, Message<OrderEvent> message,
                               Transition<OrderState, OrderEvent> transition,
                               StateMachine<OrderState, OrderEvent> stateMachine) {

        log.debug("Pre State change called");

        Optional.ofNullable(message)
                .flatMap(msg -> Optional.ofNullable((String) msg.getHeaders().getOrDefault(OrderManagerImpl.ORDER_ID_HEADER, " ")))
                .ifPresent(orderId -> {
                    log.debug("Saving state for order id: " + orderId + " Status: " + state.getId());

                    Order order = orderRepository.getOne(Long.valueOf(orderId));
                    order.setOrderState(state.getId());
                    orderRepository.saveAndFlush(order);
                });

    }
}
