package com.nexusmarket.model.orders;

public class OrderItem {
    private String productId;
    private int quantity;
    private double price;

    public OrderItem(String productId, int quantity, double price) {
        if (productId == null || productId.isBlank()) {
            throw new IllegalArgumentException("El identificador del producto del pedido es obligatorio.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad del producto debe ser mayor que cero.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("El precio del producto no puede ser negativo.");
        }

        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad del producto debe ser mayor que cero.");
        }
        this.quantity = quantity;
    }

    public double getPrice() { return price; }
    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("El precio del producto no puede ser negativo.");
        }
        this.price = price;
    }
}
