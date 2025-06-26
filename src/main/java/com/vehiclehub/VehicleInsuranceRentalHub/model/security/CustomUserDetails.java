package com.vehiclehub.VehicleInsuranceRentalHub.model.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.vehiclehub.VehicleInsuranceRentalHub.model.common.Admin;
import com.vehiclehub.VehicleInsuranceRentalHub.model.common.Agent;

import java.util.Arrays;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final String email;
    private final String password;
    private final String role;

    public CustomUserDetails(Admin admin) {
        this.email = admin.getEmail();
        this.password = admin.getPassword();
        this.role = "ROLE_ADMIN";
    }

    public CustomUserDetails(Agent agent) {
        this.email = agent.getEmail();
        this.password = agent.getPassword();
        this.role = "ROLE_AGENT";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
