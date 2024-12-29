package br.com.amcom.order.service;

import br.com.amcom.order.dto.request.OrderItemRequest;
import br.com.amcom.order.dto.request.OrderRequest;
import br.com.amcom.order.dto.response.OrderResponse;
import br.com.amcom.order.exception.InvalidOrderException;
import br.com.amcom.order.mapper.OrderMapper;
import br.com.amcom.order.model.Order;
import br.com.amcom.order.model.OrderItem;
import br.com.amcom.order.repository.OrderRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    private final static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public OrderResponse createOrder(OrderRequest orderRequest) throws InvalidOrderException {

        validateOrder(orderRequest);

        Order order = orderRepository.save(orderMapper.toModel(orderRequest));
        return orderMapper.toResponse(order);
    }

    public List<OrderResponse> getOrders() {
        return orderRepository.findAll().stream().map(orderMapper::toResponse).toList();
    }

    private void validateOrder(OrderRequest orderRequest) {
        Set<ConstraintViolation<OrderRequest>> violations = validator.validate(orderRequest);
        if (!violations.isEmpty()) {
            List<String> errors = violations.stream().map(ConstraintViolation::getMessage).toList();
            throw new InvalidOrderException(errors);
        }

        validateOrderItems(orderRequest);

        validateDuplicatedOrder(orderRequest);
    }

    private void validateOrderItems(OrderRequest orderRequest) {
        Set<ConstraintViolation<OrderItemRequest>> violationsItems = orderRequest.getItems().stream()
                .flatMap(item -> validator.validate(item).stream())
                .collect(Collectors.toSet());
        if (!violationsItems.isEmpty()) {
            List<String> errors = violationsItems.stream().map(ConstraintViolation::getMessage).toList();
            throw new InvalidOrderException(errors);
        }
    }

    private void validateDuplicatedOrder(OrderRequest orderRequest) {
        List<OrderItem> items = orderMapper.toItems(orderRequest);
        if (orderRepository.existsByItems(items)) {
            throw new InvalidOrderException(List.of("Pedido duplicado"));
        }
    }

}
