package br.com.amcom.order.service;

import br.com.amcom.order.dto.request.OrderItemRequest;
import br.com.amcom.order.dto.request.OrderRequest;
import br.com.amcom.order.dto.response.OrderResponse;
import br.com.amcom.order.exception.InvalidOrderException;
import br.com.amcom.order.mapper.OrderMapper;
import br.com.amcom.order.model.Order;
import br.com.amcom.order.model.OrderItem;
import br.com.amcom.order.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockitoBean
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Test
    void whenCreateOrder_shouldReturnOrderDetails() {
        Order order = new Order("", List.of(
                new OrderItem("Produto 1", 1, 1.5d),
                new OrderItem("Produto 2", 2, 2.5d)
        ), 6.5d);
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);

        OrderRequest orderRequest = new OrderRequest(List.of(
                new OrderItemRequest("Produto 1", 1, 1.5d),
                new OrderItemRequest("Produto 2", 2, 2.5d)));
        OrderResponse response = orderService.createOrder(orderRequest);

        OrderResponse expectedResponse = orderMapper.toResponse(order);
        Assertions.assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    void whenGetOrders_shouldReturnOrders() {
        List<Order> orders = List.of(
                new Order("", List.of(
                        new OrderItem("Produto 1", 1, 1.5d),
                        new OrderItem("Produto 2", 2, 2.5d)
                ), 6.5d),
                new Order("", List.of(
                        new OrderItem("Produto 3", 3, 3.5d),
                        new OrderItem("Produto 4", 4, 4.5d)
                ), 10.5d)
        );
        Mockito.when(orderRepository.findAll()).thenReturn(orders);

        List<OrderResponse> response = orderService.getOrders();
        List<OrderResponse> expectedResponse = orders.stream().map(orderMapper::toResponse).toList();

        Assertions.assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    void whenCreateEmptyOrder_shouldReturnException() {
        OrderRequest orderRequest = new OrderRequest(List.of());

        InvalidOrderException exception = assertThrows(InvalidOrderException.class,
                () -> orderService.createOrder(orderRequest));
        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getErrors()).hasSize(1);
        Assertions.assertThat(exception.getErrors().get(0))
                .isEqualTo("Pedido deve ter pelo menos 1 item");
        Mockito.verify(orderRepository, Mockito.never()).save(Mockito.any(Order.class));
    }

    @Test
    void whenCreateOrderWithNullProductName_shouldReturnException() {
        OrderRequest orderRequest = new OrderRequest(List.of(
                new OrderItemRequest(null, 1, 1.5d)
        ));

        InvalidOrderException exception = assertThrows(InvalidOrderException.class,
                () -> orderService.createOrder(orderRequest));
        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getErrors()).hasSize(1);
        Assertions.assertThat(exception.getErrors().get(0))
                .isEqualTo("Produto deve ter uma descrição");
        Mockito.verify(orderRepository, Mockito.never()).save(Mockito.any(Order.class));
    }

    @Test
    void whenCreateOrderWithEmptyProductName_shouldReturnException() {
        OrderRequest orderRequest = new OrderRequest(List.of(
                new OrderItemRequest("", 1, 1.5d)
        ));

        InvalidOrderException exception = assertThrows(InvalidOrderException.class,
                () -> orderService.createOrder(orderRequest));
        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getErrors()).hasSize(1);
        Assertions.assertThat(exception.getErrors().get(0))
                .isEqualTo("Produto deve ter uma descrição");
        Mockito.verify(orderRepository, Mockito.never()).save(Mockito.any(Order.class));
    }

    @Test
    void whenCreateOrderWithZeroQuantityProduct_shouldReturnException() {
        OrderRequest orderRequest = new OrderRequest(List.of(
                new OrderItemRequest("Produto", 0, 1.5d)
        ));

        InvalidOrderException exception = assertThrows(InvalidOrderException.class,
                () -> orderService.createOrder(orderRequest));
        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getErrors()).hasSize(1);
        Assertions.assertThat(exception.getErrors().get(0))
                .isEqualTo("Produto deve ter a quantidade mínima de 1 no pedido");
        Mockito.verify(orderRepository, Mockito.never()).save(Mockito.any(Order.class));
    }

    @Test
    void whenCreateOrderWithNegativeQuantityProduct_shouldReturnException() {
        OrderRequest orderRequest = new OrderRequest(List.of(
                new OrderItemRequest("Produto", -1, 1.5d)
        ));

        InvalidOrderException exception = assertThrows(InvalidOrderException.class,
                () -> orderService.createOrder(orderRequest));
        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getErrors()).hasSize(1);
        Assertions.assertThat(exception.getErrors().get(0))
                .isEqualTo("Produto deve ter a quantidade mínima de 1 no pedido");
        Mockito.verify(orderRepository, Mockito.never()).save(Mockito.any(Order.class));
    }

    @Test
    void whenCreateOrderWithNegativePriceProduct_shouldReturnException() {
        OrderRequest orderRequest = new OrderRequest(List.of(
                new OrderItemRequest("Produto", 1, -1d)
        ));

        InvalidOrderException exception = assertThrows(InvalidOrderException.class,
                () -> orderService.createOrder(orderRequest));
        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getErrors()).hasSize(1);
        Assertions.assertThat(exception.getErrors().get(0))
                .isEqualTo("Produto deve ter um preço maior do que 0");
        Mockito.verify(orderRepository, Mockito.never()).save(Mockito.any(Order.class));
    }

    @Test
    void whenCreateDuplicateOrder_shouldReturnException() {
        Mockito.when(orderRepository.existsByItems(Mockito.anyList())).thenReturn(true);

        OrderRequest orderRequest = new OrderRequest(List.of(
                new OrderItemRequest("Produto 1", 1, 1.5d),
                new OrderItemRequest("Produto 2", 2, 2.5d)));

        InvalidOrderException exception = assertThrows(InvalidOrderException.class,
                () -> orderService.createOrder(orderRequest));
        Assertions.assertThat(exception).isNotNull();
        Assertions.assertThat(exception.getErrors()).hasSize(1);
        Assertions.assertThat(exception.getErrors().get(0))
                .isEqualTo("Pedido duplicado");
        Mockito.verify(orderRepository, Mockito.never()).save(Mockito.any(Order.class));
    }

}
