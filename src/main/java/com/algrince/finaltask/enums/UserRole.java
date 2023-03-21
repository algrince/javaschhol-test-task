package com.algrince.finaltask.enums;

public enum UserRole {
    BUYER("BUYER"),
    EMPLOYEE("EMPLOYEE"),
    ADMIN("ADMIN");

    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
