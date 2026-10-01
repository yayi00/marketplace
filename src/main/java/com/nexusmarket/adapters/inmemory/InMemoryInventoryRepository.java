package com.nexusmarket.adapters.inmemory;

import com.nexusmarket.model.inventory.Inventory;
import com.nexusmarket.ports.outbound.InventoryRepositoryPort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryInventoryRepository implements InventoryRepositoryPort {
    private final Map<String, Inventory> inventoryById = new HashMap<>();

    @Override
    public Inventory save(Inventory inventory) {
        if (inventory == null) {
            throw new IllegalArgumentException("El inventario no puede ser nulo.");
        }
        inventoryById.put(inventory.getId(), inventory);
        return inventory;
    }

    @Override
    public Optional<Inventory> findById(String id) {
        return Optional.ofNullable(inventoryById.get(id));
    }

    @Override
    public List<Inventory> findByWarehouseId(String warehouseId) {
        return inventoryById.values().stream()
                .filter(inventory -> inventory.getWarehouseId().equals(warehouseId))
                .toList();
    }

    @Override
    public List<Inventory> findByProductId(String productId) {
        return inventoryById.values().stream()
                .filter(inventory -> inventory.getProductId().equals(productId))
                .toList();
    }

    @Override
    public Optional<Inventory> findByProductAndWarehouse(String productId, String warehouseId) {
        return inventoryById.values().stream()
                .filter(inventory -> inventory.getProductId().equals(productId)
                        && inventory.getWarehouseId().equals(warehouseId))
                .findFirst();
    }
}
