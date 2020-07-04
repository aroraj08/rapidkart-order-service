package com.rapidkart.orderservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Null;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderLineDto {

    @Null
    private Long id;
    private Long itemId;
    private int quantity;

    @JsonProperty("price")
    private Double pricePerItem;
}
