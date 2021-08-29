package eg.com.payment.kafka.source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import eg.com.payment.kafka.channel.PaymentChannel;
import eg.com.payment.kafka.message.PaymentEvent;
import eg.com.payment.model.PaymentStatus;

@Component
public class PaymentFailedSource {

	@Autowired
	private PaymentChannel paymentChannel;

	public void publishPaymentFailedEvent(Long orderId) {

		PaymentEvent message = new PaymentEvent();
		message.setOrderId(orderId);
		message.setPaymentStatus(PaymentStatus.PAYMENT_NOT_RECEIVED);
		MessageChannel messageChannel = paymentChannel.outboundPayment();
		messageChannel.send(MessageBuilder.withPayload(message)
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
	}

}
