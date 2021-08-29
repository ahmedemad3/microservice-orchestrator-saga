package eg.com.inventory.kafka.message;

import java.util.List;

import eg.com.inventory.model.InventoryStatus;

public class ItemEvent {

	private Long orderId;
	private List<Long> itemIdList; // is product id
	private InventoryStatus inventoryStatus;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public InventoryStatus getInventoryStatus() {
		return inventoryStatus;
	}

	public void setInventoryStatus(InventoryStatus inventoryStatus) {
		this.inventoryStatus = inventoryStatus;
	}

	public List<Long> getItemIdList() {
		return itemIdList;
	}

	public void setItemIdList(List<Long> itemIdList) {
		this.itemIdList = itemIdList;
	}

}
