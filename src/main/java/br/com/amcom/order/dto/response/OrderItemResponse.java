package br.com.amcom.order.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class OrderItemResponse {

    private String description;

    private Integer quantity;

    private Double price;

}
