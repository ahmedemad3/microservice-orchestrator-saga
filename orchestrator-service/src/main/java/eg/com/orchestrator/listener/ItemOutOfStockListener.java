package eg.com.orchestrator.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import eg.com.orchestrator.exception.ResourceNotFoundException;
import eg.com.orchestrator.kafka.channel.OrchestratorChannel;
import eg.com.orchestrator.kafka.message.ItemEvent;
import eg.com.orchestrator.model.InventoryStatus;
import eg.com.orchestrator.util.ServicesUrlUtils;
import eg.com.orchestrator.util.RestClient;

@SuppressWarnings("deprecation")
@Component
public class ItemOutOfStockListener {
	
	private static final Logger logger = LoggerFactory.getLogger(ItemOutOfStockListener.class);
	
	@Autowired
	private RestClient restClient;
	
	@Value("${order.service.endpoint}")
	private String orderEndpointUrl;
	
	@StreamListener(target = OrchestratorChannel.INPUT_INVNETORY)
	public void listenOutOfStockItem(@Payload ItemEvent itemEvent) throws ResourceNotFoundException {
		if (InventoryStatus.OUT_OF_STOCK.name().equals(itemEvent.getInventoryStatus().name())) {
			logger.info("ItemOutOfStock event received for item id: " + itemEvent.getItemIdList());
			logger.info("call order service to compensate order for id " + itemEvent.getOrderId());
			try {
				restClient.doPost(orderEndpointUrl + ServicesUrlUtils.ORDER_COMPENSATE_URL, itemEvent.getOrderId());
			} catch (ResourceNotFoundException e) {
				logger.error("Faild to compensate the order and order id is {}" , itemEvent.getOrderId());
				e.printStackTrace();
			}
		}
	}	

}
