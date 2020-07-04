package com.rapidkart.orderservice.domain;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Orders")
public class Order extends BaseEntity {

    @Builder
    public Order(Long id, Timestamp createdDate, Timestamp lastModifiedDate,
                 Long version, Long customerId, OrderStatus orderStatus, PaymentMode paymentMode,
                 Double totalAmount, Set<OrderLine> orderLines) {

        super(id, createdDate, lastModifiedDate, version);
        this.customerId = customerId;
        this.orderStatus = orderStatus;
        this.paymentMode = paymentMode;
        this.totalAmount = totalAmount;
        this.orderLines = orderLines;
    }

    private Long customerId;

    @Enumerated(value=EnumType.STRING)
    private OrderStatus orderStatus;

    @Enumerated(value = EnumType.STRING)
    private PaymentMode paymentMode;

    private Double totalAmount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    @Fetch(FetchMode.JOIN)
    private Set<OrderLine> orderLines = new HashSet<>();

    public Order addOrderLine(OrderLine orderLine) {
        orderLine.setOrder(this);
        if (this.orderLines == null) {
            orderLines = new HashSet<>();
        }
        orderLines.add(orderLine);
        return this;
    }
}
