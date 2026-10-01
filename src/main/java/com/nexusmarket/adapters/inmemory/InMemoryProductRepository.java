package com.nexusmarket.adapters.inmemory;

import com.nexusmarket.model.catalog.Product;
import com.nexusmarket.model.enums.ProductStatus;
import com.nexusmarket.ports.outbound.ProductRepositoryPort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryProductRepository implements ProductRepositoryPort {
    private final Map<String, Product> products = new HashMap<>();

    @Override
    public Product save(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }
        products.put(product.getId(), product);
        return product;
    }

    @Override
    public Optional<Product> findById(String id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public List<Product> findByStatus(ProductStatus status) {
        return products.values().stream()
                .filter(product -> product.getStatus() == status)
                .toList();
    }

    @Override
    public void delete(String id) {
        products.remove(id);
    }
}
