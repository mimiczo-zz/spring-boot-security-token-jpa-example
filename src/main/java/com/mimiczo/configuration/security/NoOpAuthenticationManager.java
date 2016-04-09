/*
 * NAMEE Mobile Api Service
 * Copyright (c) 2015. NAMEE corporation All rights reserved.
 */

package com.mimiczo.configuration.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class NoOpAuthenticationManager implements AuthenticationManager {
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        // TODO Auto-generated method stub
        return authentication;
    }
}