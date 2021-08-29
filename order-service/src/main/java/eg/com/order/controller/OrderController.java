package eg.com.order.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import eg.com.order.dto.OrderProductDto;
import eg.com.order.model.Order;
import eg.com.order.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@PostMapping(path = "/save/{customerId}", consumes = "application/json", produces = "application/json")
	@ResponseStatus(value = HttpStatus.CREATED)
	public Order createOrder(@RequestBody List<OrderProductDto> orderProducts,
			@PathVariable(name = "customerId") Long customerId) throws Exception{
		logger.info("customerId" + customerId);
		logger.info("orderProducts is:  {}", orderProducts);
		return orderService.createOrder(orderProducts, customerId);

	}

	@PostMapping("/compensate")
	public ResponseEntity<String> compensateOrder(@RequestBody Map<String, ?> map) {
		String orderId = (String) map.get("orderId") != null ? (String) map.get("orderId") : null;
		if (StringUtils.hasText(orderId)) {
			logger.info("compensate Order with order id :  {} ", orderId);
			orderService.compensateOrder(Long.valueOf(orderId));
			return new ResponseEntity<>("request placed for compensate order id " + orderId, HttpStatus.OK);
		}
		return new ResponseEntity<>("request not placed for compensate order id : " + orderId, HttpStatus.BAD_REQUEST);

	}

}
