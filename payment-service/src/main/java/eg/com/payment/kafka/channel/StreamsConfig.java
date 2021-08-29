package eg.com.payment.kafka.channel;

import org.springframework.cloud.stream.annotation.EnableBinding;

@SuppressWarnings("deprecation")
@EnableBinding(PaymentChannel.class)
public class StreamsConfig {

}
