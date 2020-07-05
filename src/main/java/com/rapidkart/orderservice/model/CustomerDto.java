package com.rapidkart.orderservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@NoArgsConstructor
@Getter
@Setter
public class CustomerDto {

    @JsonProperty("customerId")
    @Null
    private Long customerId;

    @JsonProperty("FirstName")
    @NotBlank
    private String firstName;

    @JsonProperty("LastName")
    @NotBlank
    private String lastName;

}
