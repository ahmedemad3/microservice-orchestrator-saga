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
import eg.com.orchestrator.kafka.message.OrderEvent;
import eg.com.orchestrator.model.OrderStatus;
import eg.com.orchestrator.util.ServicesUrlUtils;
import eg.com.orchestrator.util.RestClient;

@SuppressWarnings("deprecation")
@Component
public class OrderPlacedEventListener {

	private static final Logger logger = LoggerFactory.getLogger(OrderPlacedEventListener.class);

	@Autowired
	private RestClient restClient;

	@Value("${inventory.service.endpoint}")
	private String inventoryEndpointUrl;

	@StreamListener(target = OrchestratorChannel.INPUT_ORDER)
	public void listenOrderPlaced(@Payload OrderEvent orderEvent) {

		if (OrderStatus.CREATED.name().equals(orderEvent.getOrderStatus().name())) {
			logger.info("Received an OrderPlacedEvent for order id: " + orderEvent.getOrderId());
			logger.info("Going to call inventory service for order id : " + orderEvent.getOrderId());

			try {
				// Called the inventory to check the availability
				restClient.doPost(inventoryEndpointUrl + ServicesUrlUtils.INVENTORY_COMPENSATE_URL,
						orderEvent.getOrderId());
			} catch (ResourceNotFoundException e) {
				e.printStackTrace();
				logger.error("order items not availabel with order id {} ", orderEvent.getOrderId());
			}
		}
	}

}
