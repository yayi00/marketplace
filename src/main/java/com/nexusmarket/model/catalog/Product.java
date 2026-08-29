package com.nexusmarket.model.catalog;

import com.nexusmarket.model.enums.ProductStatus;

public abstract class Product {
    private String id;
    private String name;
    private double price;
    private ProductStatus status;
    private String sellerId;

    public Product(String id, String name, double price, ProductStatus status, String sellerId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
        this.sellerId = sellerId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public ProductStatus getStatus() { return status; }
    public void setStatus(ProductStatus status) { this.status = status; }

    public String getSellerId() { return sellerId; }
    public void setSellerId(String sellerId) { this.sellerId = sellerId; }
}
