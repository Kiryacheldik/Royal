package com.epam.kirillcheldishkin.entity.user;

public enum UserRole {
    ADMIN(1, "admin"), CLIENT(2, "client");
    private int roleId;
    private String role;

    public int getRoleId() {
        return roleId;
    }

    public String getRole() {
        return role;
    }

    UserRole(int roleId, String role) {
        this.roleId = roleId;
        this.role = role;
    }
}
