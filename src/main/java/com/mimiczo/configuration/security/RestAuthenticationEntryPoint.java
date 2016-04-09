/*
 * NAMEE Mobile Api Service
 * Copyright (c) 2015. NAMEE corporation All rights reserved.
 */

package com.mimiczo.configuration.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request
    		, HttpServletResponse response
    		, AuthenticationException authException) {
    	//HttpStatus.UNAUTHORIZED
    }
}