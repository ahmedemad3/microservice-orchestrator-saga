package eg.com.orchestrator.kafka.message;

import java.util.List;

import eg.com.orchestrator.model.InventoryStatus;

public class ItemEvent {

	private Long orderId;
	private List<Long> itemIdList; // is product ids
	private InventoryStatus inventoryStatus;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public List<Long> getItemIdList() {
		return itemIdList;
	}

	public void setItemIdList(List<Long> itemIdList) {
		this.itemIdList = itemIdList;
	}

	public InventoryStatus getInventoryStatus() {
		return inventoryStatus;
	}

	public void setInventoryStatus(InventoryStatus inventoryStatus) {
		this.inventoryStatus = inventoryStatus;
	}

}
