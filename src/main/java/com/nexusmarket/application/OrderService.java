package com.nexusmarket.application;

import com.nexusmarket.model.orders.Order;
import com.nexusmarket.model.enums.OrderStatus;
import com.nexusmarket.ports.inbound.ProcessOrderUseCase;
import com.nexusmarket.ports.outbound.OrderRepositoryPort;
import java.util.List;

public class OrderService implements ProcessOrderUseCase {

    private final OrderRepositoryPort orderRepository;

    public OrderService(OrderRepositoryPort orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.PENDING_PAYMENT);
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrderStatus(String orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado: " + orderId));

        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Un pedido entregado no podrá ser modificado bajo ninguna circunstancia.");
        }

        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByBuyer(String buyerId) {
        return orderRepository.findByBuyerId(buyerId);
    }

    @Override
    public Order getOrderDetails(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado: " + orderId));
    }
}
