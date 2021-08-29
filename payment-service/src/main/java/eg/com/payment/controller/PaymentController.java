package eg.com.payment.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eg.com.payment.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

	@Autowired
	private PaymentService paymentService;

	@PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> createPayment(@RequestBody Map<String, ?> map) {
		logger.info("createPayment {}", map);
		String orderId = (String) map.get("orderId") != null ? (String) map.get("orderId") : null;
		if (StringUtils.hasText(orderId)) {
			logger.info("createPayment for order id {} ", orderId);
			paymentService.createPayment(Long.valueOf(orderId));
			return new ResponseEntity<>("craete payment placed for order id" + orderId, HttpStatus.OK);
		}
		return new ResponseEntity<>("craete payment not placed for order id : " + orderId, HttpStatus.BAD_REQUEST);
	}

}
