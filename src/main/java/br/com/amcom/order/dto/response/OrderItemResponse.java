package br.com.amcom.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderItemResponse {

    private Long id;

    private String description;

    private Integer quantity;

    private Double price;

}
