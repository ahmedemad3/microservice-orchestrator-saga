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
public class OrderNotProcessedEventSource {
	
	
	@Autowired
	private OrderChannel orderChannel;

	public void publishOrderNotProcessedEvent(Long orderId) {

		OrderEvent message = new OrderEvent();
		message.setOrderId(orderId);
		message.setOrderStatus(OrderStatus.NOT_PLACED);
		
		MessageChannel messageChannel = orderChannel.outboundOrder();
		messageChannel.send(MessageBuilder.withPayload(message)
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
				.build());
	}

}
