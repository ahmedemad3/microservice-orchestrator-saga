package eg.com.order.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eg.com.order.dto.OrderProductDto;
import eg.com.order.kafka.source.OrderNotProcessedEventSource;
import eg.com.order.kafka.source.OrderPlacedEventSource;
import eg.com.order.model.Order;
import eg.com.order.model.OrderProduct;
import eg.com.order.model.OrderStatus;
import eg.com.order.repository.OrderRepository;
import eg.com.order.service.OrderService;

@Service
@Transactional
public class OrderServiceImp implements OrderService {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImp.class);

	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderPlacedEventSource orderPlacedEventSource;
	
	@Autowired
	private OrderNotProcessedEventSource OrderNotProcessedEventSource;

	@Override
	public Iterable<Order> getAllOrders() {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public void update(Order order) {
		// should have updated by and updated on 
		// should have log entry to database
		this.orderRepository.save(order);
	}

	@Override
	public Order createOrder(List<OrderProductDto> orderProducts , Long customerId) {
		
		logger.info("create order for customer id : " + customerId);
		
		Order order = new Order();
		order.setOrderStatus(OrderStatus.CREATED);
		order.setCreatedOn(LocalDate.now());
        order.setCreatedBy("customerX");
        order.setCustomerId(customerId);
        
		List<OrderProduct> orderProductList = new ArrayList<>();
		for (OrderProductDto dto : orderProducts) {
			orderProductList.add(
					new OrderProduct(order, 
							dto.getProductDto().getId(), 
							dto.getQuantity()));
		}
		order.setOrderProducts(orderProductList);
		
		// save order
		order = this.orderRepository.save(order);
		
		// publish order event
		orderPlacedEventSource.publishOrderEvent(order.getId() , customerId);
		
		return order;
	}


	@Override
	public void compensateOrder(Long orderId) {
		
		// mark order as not placed by change its status
		Order order = new Order();
		order.setId(orderId);
		order.setOrderStatus(OrderStatus.NOT_PLACED);
		update(order);
		
		//publish OoderNotProcessedEvent
		OrderNotProcessedEventSource.publishOrderNotProcessedEvent(orderId);
		
	}

}
