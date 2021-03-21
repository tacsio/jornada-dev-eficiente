package io.tacsio.mercadolivre.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static io.tacsio.mercadolivre.config.security.ApplicationPermission.*;

public enum ApplicationRole {
    USER(Set.of(USER_READ, CATEGORY_WRITE, CATEGORY_READ, PRODUCT_READ, PRODUCT_WRITE));

    private final Set<ApplicationPermission> permissions;

    ApplicationRole(Set<ApplicationPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationPermission> getPermissions() {
        return permissions;
    }

    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = permissions.stream()
                .map(ApplicationPermission::getPermission)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
}
