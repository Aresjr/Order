package br.com.amcom.order.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderItem {

    private String description;

    private Integer quantity;

    private Double price;

}
