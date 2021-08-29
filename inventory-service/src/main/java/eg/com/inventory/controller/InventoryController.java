package eg.com.inventory.controller;

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

import eg.com.inventory.service.InventoryService;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

	private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

	@Autowired
	private InventoryService inventoryService;

	@PostMapping("/fetch/item/{orderId}")
	public ResponseEntity<String> fetchItem(@RequestBody Map<String, ?> map) {
		String orderId = (String) map.get("orderId") != null ? (String) map.get("orderId") : null;
		if (StringUtils.hasText(orderId)) {
			logger.info("fetchItem from inventory with order id {} ", orderId);
			inventoryService.fetchItem(Long.valueOf(orderId));
			return new ResponseEntity<>("request placed for order id : " + orderId, HttpStatus.OK);
		}
		return new ResponseEntity<>("request not placed for order id : " + orderId, HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/compensate/item")
	public ResponseEntity<String> compensateItem(@RequestBody Map<String, ?> map) {
		String orderId = (String) map.get("orderId") != null ? (String) map.get("orderId") : null;
		if (StringUtils.hasText(orderId)) {
			logger.info("compensateOrder from inventory with order id {} ", orderId);
			inventoryService.compensateItem(Long.valueOf(orderId));
			return new ResponseEntity<>("request placed for compensate order id" + orderId, HttpStatus.OK);
		}
		return new ResponseEntity<>("request not placed for compensate item with  order id : " + orderId,
				HttpStatus.BAD_REQUEST);

	}

}
