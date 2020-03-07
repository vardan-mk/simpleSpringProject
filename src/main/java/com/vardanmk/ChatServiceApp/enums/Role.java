package com.vardanmk.ChatServiceApp.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    //=================  User Access Enum =======================
    ADMIN,USER;

    public String getAuthority() {
        return name();
    }
}
