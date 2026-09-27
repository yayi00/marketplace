package com.nexusmarket.application;

import com.nexusmarket.model.catalog.Product;
import com.nexusmarket.model.enums.ProductStatus;
import com.nexusmarket.ports.inbound.ManageCatalogUseCase;
import com.nexusmarket.ports.outbound.ProductRepositoryPort;
import java.util.List;

public class CatalogService implements ManageCatalogUseCase {

    private final ProductRepositoryPort productRepository;

    public CatalogService(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product registerProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProductStatus(String productId, String statusStr) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + productId));

        ProductStatus status = ProductStatus.valueOf(statusStr);
        product.setStatus(status);
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(String productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + productId));
    }
}
