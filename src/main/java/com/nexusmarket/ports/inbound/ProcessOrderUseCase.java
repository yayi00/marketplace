package com.nexusmarket.ports.inbound;

import com.nexusmarket.model.orders.Order;
import com.nexusmarket.model.enums.OrderStatus;
import java.util.List;

public interface ProcessOrderUseCase {
    Order createOrder(Order order);
    Order updateOrderStatus(String orderId, OrderStatus newStatus);
    List<Order> getOrdersByBuyer(String buyerId);
    Order getOrderDetails(String orderId);
}
