package com.rapidkart.orderservice.config;

import com.rapidkart.orderservice.domain.OrderEvent;
import com.rapidkart.orderservice.domain.OrderState;
import com.rapidkart.orderservice.sm.action.ValidateOrderAction;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
@RequiredArgsConstructor
public class StateMachineConfiguration extends StateMachineConfigurerAdapter<OrderState, OrderEvent> {

    private final ValidateOrderAction validateOrderAction;

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
        transitions
                .withExternal()
                    .source(OrderState.NEW).target(OrderState.VALIDATION_PENDING)
                    .event(OrderEvent.VALIDATE_ORDER)
                    .action(validateOrderAction)
                .and().withExternal()
                    .source(OrderState.VALIDATION_PENDING).target(OrderState.VALIDATED)
                    .event(OrderEvent.VALIDATION_SUCCESS)
                .and().withExternal()
                    .source(OrderState.VALIDATION_PENDING).target(OrderState.VALIDATION_ERROR)
                    .event(OrderEvent.VALIDATION_FAILURE)
                .and().withExternal()
                    .source(OrderState.VALIDATED).target(OrderState.ALLOCATION_PENDING)
                    .event(OrderEvent.ALLOCATE_ORDER)
                .and().withExternal()
                    .source(OrderState.ALLOCATION_PENDING).target(OrderState.ALLOCATION_DONE)
                    .event(OrderEvent.ALLOCATION_SUCCESS)
                .and().withExternal()
                    .source(OrderState.ALLOCATION_PENDING).target(OrderState.ALLOCATION_ERROR)
                    .event(OrderEvent.ALLOCATION_FAILURE)
                .and().withExternal()
                    .source(OrderState.ALLOCATION_DONE).target(OrderState.ORDER_PLACED)
                    .event(OrderEvent.PLACE_ORDER);

    }
}
