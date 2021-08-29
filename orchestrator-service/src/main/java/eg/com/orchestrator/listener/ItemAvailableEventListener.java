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
public class ItemAvailableEventListener {

	private static final Logger logger = LoggerFactory.getLogger(ItemAvailableEventListener.class);

	@Autowired
	private RestClient restClient;

	@Value("${payment.service.endpoint}")
	private String paymentEndpointUrl;

	@StreamListener(target = OrchestratorChannel.INPUT_INVNETORY)
	public void listenItemAvailableEvent(@Payload ItemEvent itemEvent) throws ResourceNotFoundException {

		if (InventoryStatus.AVAILABLE.name().equals(itemEvent.getInventoryStatus().name())) {
			logger.info("Received an ItemAvailableEvent for ITEM id: " + itemEvent.getItemIdList());

			if (itemEvent.getOrderId() != null && itemEvent.getItemIdList() != null && itemEvent.getItemIdList().size() > 0) {
				logger.info("call payment service to deduct for order id : " + itemEvent.getOrderId());
				restClient.doPost(paymentEndpointUrl + ServicesUrlUtils.CREATE_PAYMENT_URL, itemEvent.getOrderId());
			}
		}

	}

}
