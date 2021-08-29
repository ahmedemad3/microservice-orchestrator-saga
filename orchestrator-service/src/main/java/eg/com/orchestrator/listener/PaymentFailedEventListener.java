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
import eg.com.orchestrator.kafka.message.PaymentEvent;
import eg.com.orchestrator.model.PaymentStatus;
import eg.com.orchestrator.util.ServicesUrlUtils;
import eg.com.orchestrator.util.RestClient;

@SuppressWarnings("deprecation")
@Component
public class PaymentFailedEventListener {

	private static final Logger logger = LoggerFactory.getLogger(PaymentFailedEventListener.class);

	@Autowired
	private RestClient restClient;

	@Value("${inventory.service.endpoint}")
	private String inventoryEndpointUrl;

	@StreamListener(target = OrchestratorChannel.INPUT_PAYMENT)
	public void listenPaymentFailed(@Payload PaymentEvent paymentEvent) throws ResourceNotFoundException {

		if (PaymentStatus.PAYMENT_NOT_RECEIVED.name().equals(paymentEvent.getPaymentStatus().name())) {
			logger.info("Received an PaymentFailedEvent for order id: " + paymentEvent.getOrderId());
			if (paymentEvent.getOrderId() != null) {
				logger.info("call item service to compensate item for order id : " + paymentEvent.getOrderId());
				restClient.doPost(inventoryEndpointUrl + ServicesUrlUtils.INVENTORY_COMPENSATE_URL,
						paymentEvent.getOrderId());
			}
		}

	}

}
