package com.nexusmarket.model.orders;

import com.nexusmarket.model.enums.OrderStatus;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String id;
    private String buyerId;
    private List<OrderItem> items = new ArrayList<>();
    private double totalAmount;
    private OrderStatus status;

    public Order(String id, String buyerId, OrderStatus status) {
        this.id = id;
        this.buyerId = buyerId;
        this.status = status;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getBuyerId() { return buyerId; }
    public void setBuyerId(String buyerId) { this.buyerId = buyerId; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; recalcTotal(); }

    public double getTotalAmount() { return totalAmount; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public void addItem(OrderItem item) { this.items.add(item); recalcTotal(); }

    public void removeItem(OrderItem item) { this.items.remove(item); recalcTotal(); }

    private void recalcTotal() {
        this.totalAmount = this.items.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
    }
}
