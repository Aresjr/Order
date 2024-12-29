package br.com.amcom.order.mapper;

import br.com.amcom.order.dto.request.OrderRequest;
import br.com.amcom.order.dto.response.OrderResponse;
import br.com.amcom.order.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    private final ModelMapper modelMapper;

    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Order toModel(OrderRequest orderRequest) {
        return modelMapper.map(orderRequest, Order.class);
    }

    public OrderResponse toResponse(Order order) {
        return modelMapper.map(order, OrderResponse.class);
    }
}
