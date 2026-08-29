package com.nexusmarket.model.inventory;

import com.nexusmarket.model.enums.WarehouseType;

public class Warehouse {
    private String id;
    private String name;
    private String address;
    private WarehouseType type;

    public Warehouse(String id, String name, String address, WarehouseType type) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.type = type;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public WarehouseType getType() { return type; }
    public void setType(WarehouseType type) { this.type = type; }
}
