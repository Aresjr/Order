package br.com.amcom.order;

import br.com.amcom.order.controller.OrderController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderApplicationTests {

	@Autowired
	private OrderController orderController;

	@Test
	void contextLoads() {
		Assertions.assertThat(orderController).isNotNull();
	}

}
