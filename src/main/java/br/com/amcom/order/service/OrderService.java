package br.com.amcom.order.service;

import br.com.amcom.order.dto.request.OrderRequest;
import br.com.amcom.order.dto.response.OrderResponse;
import br.com.amcom.order.exception.InvalidOrderException;
import br.com.amcom.order.mapper.OrderMapper;
import br.com.amcom.order.model.Order;
import br.com.amcom.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    public OrderResponse createOrder(OrderRequest orderRequest) throws InvalidOrderException {

        validateOrder(orderRequest);

        Order order = orderRepository.save(orderMapper.toModel(orderRequest));
        return orderMapper.toResponse(order);
    }

    public List<OrderResponse> getOrders() {
        return orderRepository.findAll().stream().map(orderMapper::toResponse).toList();
    }

    private void validateOrder(OrderRequest orderRequest) {
        orderRequest.getItems().forEach(item -> {
            if (item.getDescription() == null || item.getDescription().isEmpty()) {
                throw new InvalidOrderException("Descrição do item é obrigatório");
            }
            if (item.getQuantity() == null || item.getQuantity() <= 0) {
                throw new InvalidOrderException("Quantidade deve ser maior do que 0");
            }
            if (item.getPrice() == null || item.getPrice() <= 0) {
                throw new InvalidOrderException("Preço deve ser maior do que 0");
            }
        });
    }

}
