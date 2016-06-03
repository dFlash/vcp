package com.maliavin.vcp.security;

import java.util.Collection;
import java.util.Collections;

import javax.annotation.Nonnull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import com.maliavin.vcp.Constants;
import com.maliavin.vcp.domain.User;

public final class CurrentUser implements UserDetails {

    private static final long serialVersionUID = 4474011966837013929L;

    private static final String ROLE_USER = "User";

    private final User user;

    public CurrentUser(@Nonnull User account) {
        super();
        Assert.notNull(account);
        this.user = account;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user.getRole().equalsIgnoreCase(ROLE_USER)) {
            return Collections.singleton(new SimpleGrantedAuthority(Constants.Role.USER.name()));
        } else {
            return Collections.singleton(new SimpleGrantedAuthority(Constants.Role.ADMIN.name()));
        }
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CurrentUser other = (CurrentUser) obj;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }
}
