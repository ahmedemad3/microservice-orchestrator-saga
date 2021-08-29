package eg.com.orchestrator.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import eg.com.orchestrator.exception.ResourceNotFoundException;
import eg.com.orchestrator.kafka.channel.OrchestratorChannel;
import eg.com.orchestrator.kafka.message.PaymentEvent;
import eg.com.orchestrator.model.PaymentStatus;

@SuppressWarnings("deprecation")
@Component
public class PaymentReceivedEventListener {

	private static final Logger logger = LoggerFactory.getLogger(PaymentReceivedEventListener.class);

	@StreamListener(target = OrchestratorChannel.INPUT_PAYMENT)
	public void listenItemFetchedEvent(@Payload PaymentEvent paymentEvent) throws ResourceNotFoundException {

		if (PaymentStatus.PAYMENT_RECEIVED.name().equals(paymentEvent.getPaymentStatus().name())) {
			logger.info("Received an PaymentReceivedEvent for order id: " + paymentEvent.getOrderId());
			if (paymentEvent.getOrderId() != null) {
				logger.info("call shipment service or notify service to notify customer for order id : "
						+ paymentEvent.getOrderId());
				logger.info("call order service to update order status for order id : " + paymentEvent.getOrderId());
			}
		}

	}

}
