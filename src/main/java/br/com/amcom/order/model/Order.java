package br.com.amcom.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@Document(collection = "orders")
@Getter
@NoArgsConstructor
@Setter
public class Order {

    @Id
    private String id;

    private List<OrderItem> items;

    private Double totalPrice;

}
