package com.nexusmarket.model.users;

import com.nexusmarket.model.enums.UserRole;
import com.nexusmarket.model.enums.UserStatus;
import java.util.List;

public class Buyer extends User {
    private String mainAddress;
    private List<String> additionalAddresses;
    private String commercialStatus;

    public Buyer(String id, String fullName, String email, UserStatus status,
                 String mainAddress, List<String> additionalAddresses, String commercialStatus) {
        super(id, fullName, email, UserRole.BUYER, status);
        this.mainAddress = mainAddress;
        this.additionalAddresses = additionalAddresses;
        this.commercialStatus = commercialStatus;
    }

    public String getMainAddress() { return mainAddress; }
    public void setMainAddress(String mainAddress) { this.mainAddress = mainAddress; }

    public List<String> getAdditionalAddresses() { return additionalAddresses; }
    public void setAdditionalAddresses(List<String> additionalAddresses) { this.additionalAddresses = additionalAddresses; }

    public String getCommercialStatus() { return commercialStatus; }
    public void setCommercialStatus(String commercialStatus) { this.commercialStatus = commercialStatus; }
}
