package com.nexusmarket.model.catalog;

import com.nexusmarket.model.enums.ProductStatus;

public class DigitalProduct extends Product {
    private String downloadUrl;
    private double fileSize;

    public DigitalProduct(String id, String name, double price, ProductStatus status, String sellerId,
                          String downloadUrl, double fileSize) {
        super(id, name, price, status, sellerId);
        this.downloadUrl = downloadUrl;
        this.fileSize = fileSize;
    }

    public String getDownloadUrl() { return downloadUrl; }
    public void setDownloadUrl(String downloadUrl) { this.downloadUrl = downloadUrl; }

    public double getFileSize() { return fileSize; }
    public void setFileSize(double fileSize) { this.fileSize = fileSize; }
}
