package br.com.amcom.order.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
@Setter
public class OrderResponse extends Response {

    private String id;

    private List<OrderItemResponse> items;

    private Double totalPrice;

}
