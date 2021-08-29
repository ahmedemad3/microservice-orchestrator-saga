package eg.com.order.kafka.source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import eg.com.order.kafka.channel.OrderChannel;
import eg.com.order.kafka.message.OrderEvent;
import eg.com.order.model.OrderStatus;

@Component
public class OrderPlacedEventSource {
	
	@Autowired
	private OrderChannel orderChannel;

	public void publishOrderEvent(Long orderId , Long customerId) {

		OrderEvent message = new OrderEvent();
		message.setOrderId(orderId);
		message.setCustomerId(customerId);
		message.setOrderStatus(OrderStatus.CREATED);
		
		
		MessageChannel messageChannel = orderChannel.outboundOrder();
		messageChannel.send(MessageBuilder.withPayload(message)
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
				.build());
	}

}
