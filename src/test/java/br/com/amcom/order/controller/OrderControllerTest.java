package br.com.amcom.order.controller;

import br.com.amcom.order.dto.request.OrderItemRequest;
import br.com.amcom.order.dto.request.OrderRequest;
import br.com.amcom.order.dto.response.ErrorResponse;
import br.com.amcom.order.dto.response.OrderItemResponse;
import br.com.amcom.order.dto.response.OrderResponse;
import br.com.amcom.order.exception.InvalidOrderException;
import br.com.amcom.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final static String URL_ORDERS = "/orders";

    @Test
    void whenCreateOrder_shouldReturnOkWithOrderDetails() throws Exception {
        OrderRequest orderRequest = new OrderRequest(List.of(
                new OrderItemRequest("Produto 1", 1, 1.5d),
                new OrderItemRequest("Produto 2", 2, 2.5d)));

        OrderResponse response = new OrderResponse("", List.of(
                new OrderItemResponse("Produto 1", 1, 1.5d),
                new OrderItemResponse("Produto 2", 2, 2.5d)
        ), 6.5d);

        Mockito.when(orderService.createOrder(Mockito.any(OrderRequest.class))).thenReturn(response);

        String request = objectMapper.writeValueAsString(orderRequest);

        this.mockMvc.perform(
                        post(URL_ORDERS)
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    @Test
    void whenCreateOrderEmptyFields_shouldReturnException() throws Exception {
        OrderRequest orderRequest = new OrderRequest(List.of());

        ErrorResponse response = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                List.of("Pedido deve conter algum produto"));

        Mockito.when(orderService.createOrder(Mockito.any(OrderRequest.class)))
                .thenThrow(new InvalidOrderException(List.of("Pedido deve conter algum produto")));

        String request = objectMapper.writeValueAsString(orderRequest);

        this.mockMvc.perform(
                        post(URL_ORDERS)
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    @Test
    void whenGetOrders_shouldReturnOrders() throws Exception {
        List<OrderResponse> response = List.of(
                new OrderResponse("", List.of(
                        new OrderItemResponse("Produto 1", 1, 1.5d),
                        new OrderItemResponse("Produto 2", 2, 2.5d)
                ), 6.5d)
        );
        Mockito.when(orderService.getOrders()).thenReturn(response);

        this.mockMvc.perform(
                        get(URL_ORDERS)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(response)));
    }

    @Test
    void whenGetInvalidUrl_shouldReturnNotFound() throws Exception {
        this.mockMvc.perform(
                        get("/invalid")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

}
