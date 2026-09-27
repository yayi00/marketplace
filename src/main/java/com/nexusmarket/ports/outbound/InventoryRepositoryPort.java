package com.nexusmarket.ports.outbound;

import com.nexusmarket.model.inventory.Inventory;
import java.util.List;
import java.util.Optional;

public interface InventoryRepositoryPort {
    Inventory save(Inventory inventory);
    Optional<Inventory> findById(String id);
    List<Inventory> findByWarehouseId(String warehouseId);
    List<Inventory> findByProductId(String productId);
    Optional<Inventory> findByProductAndWarehouse(String productId, String warehouseId);
}
