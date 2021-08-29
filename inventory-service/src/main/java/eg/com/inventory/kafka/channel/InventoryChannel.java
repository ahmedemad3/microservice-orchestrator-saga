package eg.com.inventory.kafka.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

@SuppressWarnings("deprecation")
public interface InventoryChannel {

	String OUTPUT = "inventory-out";

	@Output(OUTPUT)
	MessageChannel outboundInventory();
}
