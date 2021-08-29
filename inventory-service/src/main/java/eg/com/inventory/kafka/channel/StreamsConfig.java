package eg.com.inventory.kafka.channel;

import org.springframework.cloud.stream.annotation.EnableBinding;

@SuppressWarnings("deprecation")
@EnableBinding(InventoryChannel.class)
public class StreamsConfig {

}
