package br.com.amcom.order.repository;

import br.com.amcom.order.model.Order;
import br.com.amcom.order.model.OrderItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {

    boolean existsByItems(List<OrderItem> items);

}
