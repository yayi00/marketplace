package com.nexusmarket.model.users;

import com.nexusmarket.model.enums.UserStatus;

public class Administrator extends User {
    private String department;

    public Administrator(String id, String fullName, String email, UserStatus status, String department) {
        super(id, fullName, email, com.nexusmarket.model.enums.UserRole.ADMINISTRATOR, status);
        this.department = department;
    }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}
