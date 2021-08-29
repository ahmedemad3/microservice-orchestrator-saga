package eg.com.payment.kafka.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

@SuppressWarnings("deprecation")
public interface PaymentChannel {

	String OUTPUT = "payment-out";

	@Output(OUTPUT)
	MessageChannel outboundPayment();

}
