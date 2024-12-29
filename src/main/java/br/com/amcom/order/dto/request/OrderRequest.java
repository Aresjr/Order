package br.com.amcom.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class OrderRequest {

    private List<OrderItemRequest> items;

}
