package com.nexusmarket.model.users;

import com.nexusmarket.model.enums.UserStatus;

public class LogisticsOperator extends User {
    private String region;

    public LogisticsOperator(String id, String fullName, String email, UserStatus status, String region) {
        super(id, fullName, email, com.nexusmarket.model.enums.UserRole.LOGISTICS_OPERATOR, status);
        this.region = region;
    }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
}
