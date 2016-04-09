package com.mimiczo.configuration.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collection;

/**
 * Created by mimiczo on 2015.12.24
 */
public class JWTAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private Object details;

    Collection authorities;
    public JWTAuthenticationToken(String jwtToken) {
        super(null);
        super.setAuthenticated(true); // must use super, as we override
        //parser token
        //JWEParser parser = new JWTParser(jwtToken);
        //this.principal=parser.getSub();
        this.principal="";
        this.setDetailsAuthorities();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    private void setDetailsAuthorities() {
        //setting user details info
    }

    @Override
    public Collection getAuthorities() {
        return authorities;
    }
}
