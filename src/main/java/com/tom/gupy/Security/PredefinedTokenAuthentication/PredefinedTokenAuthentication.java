package com.tom.gupy.Security.PredefinedTokenAuthentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class PredefinedTokenAuthentication extends AbstractAuthenticationToken {
    public PredefinedTokenAuthentication(Collection<? extends GrantedAuthority> authorities) {
        super(Collections.emptyList());
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
