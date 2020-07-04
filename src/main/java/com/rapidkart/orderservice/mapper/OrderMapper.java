package com.rapidkart.orderservice.mapper;

import com.rapidkart.orderservice.domain.Order;
import com.rapidkart.orderservice.model.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {DateMapper.class})
public interface OrderMapper {

    @Mapping(source = "createdDate", target = "createdDate")
    Order orderDtoToOrder(OrderDto orderDto);

    OrderDto orderToOrderDto(Order order);
}
