package com.nexusmarket.application;

import com.nexusmarket.model.inventory.Inventory;
import com.nexusmarket.ports.inbound.ManageInventoryUseCase;
import com.nexusmarket.ports.outbound.InventoryRepositoryPort;
import java.util.List;

public class InventoryService implements ManageInventoryUseCase {

    private final InventoryRepositoryPort inventoryRepository;

    public InventoryService(InventoryRepositoryPort inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Inventory registerStock(Inventory inventory) {
        if (inventory.getAvailableStock() < 0) {
            throw new IllegalArgumentException("No se permiten existencias negativas en el inventario.");
        }
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory updateStockQuantity(String inventoryId, int quantity) {
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new IllegalArgumentException("Registro de inventario no encontrado: " + inventoryId));

        if (inventory.getAvailableStock() + quantity < 0) {
            throw new IllegalArgumentException("La operación generaría existencias negativas, lo cual está prohibido.");
        }

        inventory.setAvailableStock(inventory.getAvailableStock() + quantity);
        return inventoryRepository.save(inventory);
    }

    @Override
    public List<Inventory> getInventoryByWarehouse(String warehouseId) {
        return inventoryRepository.findByWarehouseId(warehouseId);
    }
}
