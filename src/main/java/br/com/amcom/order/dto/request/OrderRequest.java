package br.com.amcom.order.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class OrderRequest {

    @NotNull(message = "Pedido deve conter algum produto")
    @Size(min = 1, message = "Pedido deve ter pelo menos 1 item")
    private List<OrderItemRequest> items;

}
