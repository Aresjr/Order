package br.com.amcom.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class OrderResponse {

    private Long id;

    private List<OrderItemResponse> items;

    private Double totalPrice;

}
