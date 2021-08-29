package eg.com.order.kafka.channel;

import org.springframework.cloud.stream.annotation.EnableBinding;

@SuppressWarnings("deprecation")
@EnableBinding(OrderChannel.class)
public class StreamsConfig {

}
