package com.rapidkart.orderservice.config;

import com.rapidkart.orderservice.domain.OrderEvent;
import com.rapidkart.orderservice.domain.OrderState;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.Collections;
import java.util.EnumSet;

public class StateMachineConfiguration extends StateMachineConfigurerAdapter<OrderState, OrderEvent> {

    @Override
    public void configure(StateMachineStateConfigurer<OrderState, OrderEvent> states)
            throws Exception {
        states.withStates()
                .initial(OrderState.NEW)
                .states(EnumSet.allOf(OrderState.class))
                .end(OrderState.VALIDATION_ERROR)
                .end(OrderState.ALLOCATION_ERROR)
                .end(OrderState.ORDER_PLACED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderState, OrderEvent> transitions)
            throws Exception {
        transitions.withExternal()
                .source(OrderState.NEW).target(OrderState.VALIDATION_PENDING)
                    .event(OrderEvent.VALIDATE_ORDER)
                .source(OrderState.VALIDATION_PENDING).target(OrderState.VALIDATED)
                    .event(OrderEvent.VALIDATION_SUCCESS)
                .source(OrderState.VALIDATION_PENDING).target(OrderState.VALIDATION_ERROR)
                    .event(OrderEvent.VALIDATION_FAILURE)
                .source(OrderState.VALIDATED).target(OrderState.ALLOCATION_PENDING)
                    .event(OrderEvent.ALLOCATE_ORDER)
                .source(OrderState.ALLOCATION_PENDING).target(OrderState.ALLOCATION_DONE)
                    .event(OrderEvent.ALLOCATION_SUCCESS)
                .source(OrderState.ALLOCATION_PENDING).target(OrderState.ALLOCATION_ERROR)
                    .event(OrderEvent.ALLOCATION_FAILURE)
                .source(OrderState.ALLOCATION_DONE).target(OrderState.ORDER_PLACED)
                    .event(OrderEvent.PLACE_ORDER);

    }
}
