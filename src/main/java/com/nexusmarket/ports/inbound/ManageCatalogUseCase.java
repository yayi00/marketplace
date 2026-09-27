package com.nexusmarket.ports.inbound;

import com.nexusmarket.model.catalog.Product;
import java.util.List;

public interface ManageCatalogUseCase {
    Product registerProduct(Product product);
    Product updateProductStatus(String productId, String status);
    List<Product> getAllProducts();
    Product getProductById(String productId);
}
