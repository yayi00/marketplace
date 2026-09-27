package com.nexusmarket.ports.inbound;

import com.nexusmarket.model.inventory.Inventory;
import java.util.List;

public interface ManageInventoryUseCase {
    Inventory registerStock(Inventory inventory);
    Inventory updateStockQuantity(String inventoryId, int quantity);
    List<Inventory> getInventoryByWarehouse(String warehouseId);
}
