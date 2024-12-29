package br.com.amcom.order.controller;

import br.com.amcom.order.dto.request.OrderRequest;
import br.com.amcom.order.dto.response.ErrorResponse;
import br.com.amcom.order.dto.response.OrderResponse;
import br.com.amcom.order.dto.response.Response;
import br.com.amcom.order.exception.InvalidOrderException;
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
    public ResponseEntity<List<OrderResponse>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @PostMapping
    public ResponseEntity<Response> postOrder(@RequestBody OrderRequest orderRequest) {
        log.info("Requisição de criação de pedido: {};", orderRequest);

        OrderResponse response;

        try {
            response = orderService.createOrder(orderRequest);
        } catch (InvalidOrderException e) {
            log.warn("Erro ao criar pedido: {}", e.getMessage());
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getErrors()),
                    HttpStatus.BAD_REQUEST);
        }

        log.info("Pedido criado: {}", response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
