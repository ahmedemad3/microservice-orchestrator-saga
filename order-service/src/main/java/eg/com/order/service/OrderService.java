package eg.com.order.service;

import java.util.List;

import eg.com.order.dto.OrderProductDto;
import eg.com.order.model.Order;

public interface OrderService {
	public Iterable<Order> getAllOrders();
	public Order createOrder(List<OrderProductDto> orderProducts, Long customerId);
	public void update(Order order);
	public void compensateOrder(Long orderId);
}
