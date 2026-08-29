package com.nexusmarket.model.orders;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private String buyerId;
    private List<CartItem> items = new ArrayList<>();

    public ShoppingCart(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerId() { return buyerId; }
    public void setBuyerId(String buyerId) { this.buyerId = buyerId; }

    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }

    public void addItem(CartItem item) { this.items.add(item); }

    public void removeItem(CartItem item) { this.items.remove(item); }
}
