package eg.com.order.kafka.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

@SuppressWarnings("deprecation")
public interface OrderChannel {
	
	String OUTPUT = "order-out";

	@Output(OUTPUT)
	MessageChannel outboundOrder();

}
