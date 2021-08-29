package eg.com.inventory.service;

public interface InventoryService {

	void fetchItem(Long orderId);

	void compensateItem(Long orderId);

}
