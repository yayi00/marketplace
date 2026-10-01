package com.nexusmarket.adapters.inmemory;

import com.nexusmarket.model.enums.OrderStatus;
import com.nexusmarket.model.orders.Order;
import com.nexusmarket.ports.outbound.OrderRepositoryPort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryOrderRepository implements OrderRepositoryPort {
    private final Map<String, Order> orders = new HashMap<>();

    @Override
    public Order save(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("La orden no puede ser nula.");
        }
        orders.put(order.getId(), order);
        return order;
    }

    @Override
    public Optional<Order> findById(String id) {
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public List<Order> findByBuyerId(String buyerId) {
        return orders.values().stream()
                .filter(order -> order.getBuyerId().equals(buyerId))
                .toList();
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        return orders.values().stream()
                .filter(order -> order.getStatus() == status)
                .toList();
    }
}
