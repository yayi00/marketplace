package com.nexusmarket.model.catalog;

import com.nexusmarket.model.enums.ProductStatus;
import java.util.List;

public class PhysicalProduct extends Product {
    private double weight;
    private String dimensions;
    private List<String> variants;

    public PhysicalProduct(String id, String name, double price, ProductStatus status, String sellerId,
                           double weight, String dimensions, List<String> variants) {
        super(id, name, price, status, sellerId);
        this.weight = weight;
        this.dimensions = dimensions;
        this.variants = variants;
    }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public String getDimensions() { return dimensions; }
    public void setDimensions(String dimensions) { this.dimensions = dimensions; }

    public List<String> getVariants() { return variants; }
    public void setVariants(List<String> variants) { this.variants = variants; }
}
