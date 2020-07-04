package com.rapidkart.orderservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rapidkart.orderservice.domain.OrderStatus;
import com.rapidkart.orderservice.domain.PaymentMode;
import lombok.*;
import javax.validation.constraints.Null;
import java.time.OffsetDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDto {

    @Null
    private Long id;
    private Long customerId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ", shape = JsonFormat.Shape.STRING)
    private OffsetDateTime createdDate = null;
    private OrderStatus orderStatus;
    private PaymentMode paymentMode;
    private Double totalAmount;

    @JsonProperty("orderLines")
    private Set<OrderLineDto> orderLines;

}
