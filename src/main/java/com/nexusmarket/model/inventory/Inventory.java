package com.nexusmarket.model.inventory;

public class Inventory {
    private String id;
    private String productId;
    private String warehouseId;
    private int availableStock;
    private int reservedStock;

    public Inventory(String id, String productId, String warehouseId, int availableStock, int reservedStock) {
        this.id = id;
        this.productId = productId;
        this.warehouseId = warehouseId;
        this.availableStock = Math.max(0, availableStock);
        this.reservedStock = Math.max(0, reservedStock);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getWarehouseId() { return warehouseId; }
    public void setWarehouseId(String warehouseId) { this.warehouseId = warehouseId; }

    public int getAvailableStock() { return availableStock; }
    public void setAvailableStock(int availableStock) { this.availableStock = Math.max(0, availableStock); }

    public int getReservedStock() { return reservedStock; }
    public void setReservedStock(int reservedStock) { this.reservedStock = Math.max(0, reservedStock); }
}
