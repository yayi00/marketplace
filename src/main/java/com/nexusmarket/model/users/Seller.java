package com.nexusmarket.model.users;

import com.nexusmarket.model.enums.UserRole;
import com.nexusmarket.model.enums.UserStatus;

public class Seller extends User {
    private String companyName;
    private String taxId;

    public Seller(String id, String fullName, String email, UserStatus status,
                  String companyName, String taxId) {
        super(id, fullName, email, UserRole.SELLER, status);
        this.companyName = companyName;
        this.taxId = taxId;
    }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getTaxId() { return taxId; }
    public void setTaxId(String taxId) { this.taxId = taxId; }
}
