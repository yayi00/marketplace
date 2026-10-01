package com.nexusmarket.model.users;

import com.nexusmarket.model.enums.UserStatus;

public class Supervisor extends User {
    private String area;

    public Supervisor(String id, String fullName, String email, UserStatus status, String area) {
        super(id, fullName, email, com.nexusmarket.model.enums.UserRole.SUPERVISOR, status);
        this.area = area;
    }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }
}
