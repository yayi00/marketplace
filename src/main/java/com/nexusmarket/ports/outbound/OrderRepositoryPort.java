package com.nexusmarket.ports.outbound;

import com.nexusmarket.model.orders.Order;
import com.nexusmarket.model.enums.OrderStatus;
import java.util.List;
import java.util.Optional;

public interface OrderRepositoryPort {
    Order save(Order order);
    Optional<Order> findById(String id);
    List<Order> findByBuyerId(String buyerId);
    List<Order> findByStatus(OrderStatus status);
}
