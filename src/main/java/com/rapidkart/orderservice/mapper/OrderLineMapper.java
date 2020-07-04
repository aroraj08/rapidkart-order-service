package com.rapidkart.orderservice.mapper;

import com.rapidkart.orderservice.domain.OrderLine;
import com.rapidkart.orderservice.model.OrderLineDto;
import org.mapstruct.Mapper;

@Mapper
public interface OrderLineMapper {

    OrderLineDto orderLineToDto(OrderLine orderLine);
    OrderLine orderLineDtoToOrderLine(OrderLineDto orderLineDto);
}
