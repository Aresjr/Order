package br.com.amcom.order.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class OrderResponse extends Response {

    private String id;

    private List<OrderItemResponse> items;

    private Double totalPrice;

}
