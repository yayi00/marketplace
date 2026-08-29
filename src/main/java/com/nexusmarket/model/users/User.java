package com.nexusmarket.model.users;

import com.nexusmarket.model.enums.UserRole;
import com.nexusmarket.model.enums.UserStatus;

public abstract class User {
    private String id;
    private String fullName;
    private String email;
    private UserRole role;
    private UserStatus status;

    public User(String id, String fullName, String email, UserRole role, UserStatus status) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public UserStatus getStatus() { return status; }
    public void setStatus(UserStatus status) { this.status = status; }
}
