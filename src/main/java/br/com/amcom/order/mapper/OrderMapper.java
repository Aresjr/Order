package br.com.amcom.order.mapper;

import br.com.amcom.order.dto.request.OrderRequest;
import br.com.amcom.order.dto.response.OrderResponse;
import br.com.amcom.order.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public Order toModel(OrderRequest orderRequest) {
        Order order = objectMapper.convertValue(orderRequest, Order.class);
        order.setTotalPrice(orderRequest.getItems().stream()
                .map(item -> item.getPrice() * item.getQuantity())
                .reduce(0d, Double::sum));
        return order;
    }

    public OrderResponse toResponse(Order order) {
        return objectMapper.convertValue(order, OrderResponse.class);
    }
}
