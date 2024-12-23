package br.com.amcom.order.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @GetMapping
    public ResponseEntity<String> getOrders() {
        return ResponseEntity.ok("Orders");
    }

}
