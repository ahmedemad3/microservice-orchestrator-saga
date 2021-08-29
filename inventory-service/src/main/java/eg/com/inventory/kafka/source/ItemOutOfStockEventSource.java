package eg.com.inventory.kafka.source;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import eg.com.inventory.kafka.channel.InventoryChannel;
import eg.com.inventory.kafka.message.ItemEvent;
import eg.com.inventory.model.InventoryStatus;

@Component
public class ItemOutOfStockEventSource {
	
	@Autowired
	private InventoryChannel inventoryChannel;

	public void publishItemOutOfStockEvent(Long orderId,List<Long> itemIdList) {

		ItemEvent message = new ItemEvent();
		message.setItemIdList(itemIdList);
		message.setOrderId(orderId);
		message.setInventoryStatus(InventoryStatus.OUT_OF_STOCK);
		MessageChannel messageChannel = inventoryChannel.outboundInventory();
		messageChannel.send(MessageBuilder.withPayload(message)
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
				.build());

	}

}
