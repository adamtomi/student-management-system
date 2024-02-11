package com.vamk.backend.util;

import org.springframework.security.core.GrantedAuthority;

/*
 * This is one of those solutions that are
 * perfectly functional, however in a real
 * application a more robust solution should
 * be used. However, due to time constraints,
 * I decided to go with it.
 */
public enum Role implements GrantedAuthority {
    ADMIN, STUDENT;

    @Override
    public String getAuthority() {
        return name();
    }
}
