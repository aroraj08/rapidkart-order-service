package com.rapidkart.orderservice.serviceimpl.listeners;

import com.rapidkart.model.CustomerDto;
import com.rapidkart.model.events.ValidateOrderResult;
import com.rapidkart.orderservice.config.JmsConfig;
import com.rapidkart.orderservice.serviceimpl.OrderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidateOrderResultListener {

    private final OrderManager orderManager;

    @JmsListener(destination = JmsConfig.CUSTOMER_VALIDATION_RESPONSE_QUEUE)
    public void listen(ValidateOrderResult result) {

        orderManager.processValidationResult(result.getOrderId(), result.getIsValidUser());
    }
}
