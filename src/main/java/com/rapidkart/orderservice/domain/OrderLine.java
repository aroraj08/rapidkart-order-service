package com.rapidkart.orderservice.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name="OrderLines")
public class OrderLine extends BaseEntity {

    @Builder
    public OrderLine(Long id, Timestamp createdDate, Timestamp lastModifiedDate,
                     Long version, int quantity, Double pricePerItem, Order order, Long itemId) {
        super(id, createdDate, lastModifiedDate, version);
        this.quantity = quantity;
        this.pricePerItem = pricePerItem;
        this.order = order;
        this.itemId = itemId;
    }
    private int quantity;
    private Double pricePerItem;

    @ManyToOne
    @JoinColumn(name="orderId")
    private Order order;

    private Long itemId;

    public void setOrder(Order order) {
        this.order = order;
    }
}
