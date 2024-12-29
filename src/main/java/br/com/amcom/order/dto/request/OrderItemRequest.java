package br.com.amcom.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderItemRequest {

    private String description;

    private Integer quantity;

    private Double price;

}
