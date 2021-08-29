package eg.com.inventory.service.impl;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eg.com.inventory.kafka.source.ItemAvailableEventSource;
import eg.com.inventory.kafka.source.ItemOutOfStockEventSource;
import eg.com.inventory.service.InventoryService;

@Service
@Transactional
public class InventoryServiceImp implements InventoryService{
	
	private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);
	
	@Autowired
	private ItemAvailableEventSource itemAvailableEventSource;
	
	@Autowired
	private ItemOutOfStockEventSource itemOutOfStockEventSource;

	@Override
	public void fetchItem(Long orderId) {
		logger.info("fetch items from order service with orderId is {}" , orderId);
		/* call order service to find out list of items id for order id*/
		List<Long> itemIdList = getItemsFromOrderService(orderId);
		
		// check items in stock or not
		Boolean isInStock = areItemsExistsInStock(itemIdList);
		if(isInStock) {
			itemAvailableEventSource.publishItemAvailableEvent(orderId, itemIdList);
			logger.info("item is fetched successfully");
		}else {
			itemOutOfStockEventSource.publishItemOutOfStockEvent(orderId, itemIdList);
			logger.info("item is out of stock");
		}
	}

	private Boolean areItemsExistsInStock(List<Long> itemIdList) {
		// check items in stock DB or not
		return true;
	}

	private List<Long> getItemsFromOrderService(Long orderId) {
		List<Long> items = Arrays.asList(1L, 2L, 3L);
		return items;
	}

	@Override
	public void compensateItem(Long orderId) {
		
		/*return given list of item ids to inventory*/
		List<Long> itemIdList = getItemsFromOrderService(orderId);
		
		/*this will compensate order*/
		itemOutOfStockEventSource.publishItemOutOfStockEvent(orderId, itemIdList);
		
	}

}
