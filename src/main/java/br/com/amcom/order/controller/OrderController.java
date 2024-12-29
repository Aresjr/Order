package br.com.amcom.order.controller;

import br.com.amcom.order.dto.request.OrderRequest;
import br.com.amcom.order.dto.response.OrderResponse;
import br.com.amcom.order.model.Order;
import br.com.amcom.order.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok(List.of());
    }

    @PostMapping
    public ResponseEntity<OrderResponse> postOrder(@RequestBody OrderRequest orderRequest) {
        log.info("Requisição de criação de pedido: {};", orderRequest);

        OrderResponse response = orderService.createOrder(orderRequest);

        log.info("Pedido criado: {}", response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
