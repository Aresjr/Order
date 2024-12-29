package br.com.amcom.order.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderItemRequest {

    @NotEmpty(message = "Produto deve ter uma descrição")
    private String description;

    @Min(value = 1, message = "Produto deve ter a quantidade mínima de 1 no pedido")
    private Integer quantity;

    @DecimalMin(value = "0.01", message = "Produto deve ter um preço maior do que 0")
    private Double price;

}
