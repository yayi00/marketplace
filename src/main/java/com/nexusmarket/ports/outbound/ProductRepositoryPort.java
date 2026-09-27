package com.nexusmarket.ports.outbound;

import com.nexusmarket.model.catalog.Product;
import com.nexusmarket.model.enums.ProductStatus;
import java.util.List;
import java.util.Optional;

public interface ProductRepositoryPort {
    Product save(Product product);
    Optional<Product> findById(String id);
    List<Product> findAll();
    List<Product> findByStatus(ProductStatus status);
    void delete(String id);
}
