package br.com.amcom.order.service;

import br.com.amcom.order.dto.request.OrderRequest;
import br.com.amcom.order.dto.response.OrderResponse;
import br.com.amcom.order.mapper.OrderMapper;
import br.com.amcom.order.model.Order;
import br.com.amcom.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper modelMapper;

    public OrderResponse createOrder(OrderRequest orderRequest) {
        //TODO - check values and validate
        Order order = orderRepository.save(modelMapper.toModel(orderRequest));
        return modelMapper.toResponse(order);
    }

}
