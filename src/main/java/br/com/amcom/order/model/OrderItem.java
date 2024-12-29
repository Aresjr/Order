package br.com.amcom.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class OrderItem {

    private String description;

    private Integer quantity;

    private Double price;

}
