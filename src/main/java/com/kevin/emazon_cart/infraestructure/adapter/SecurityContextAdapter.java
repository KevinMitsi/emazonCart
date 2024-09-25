package com.kevin.emazon_cart.infraestructure.adapter;

import com.kevin.emazon_cart.domain.spi.ISecurityContextPort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextAdapter implements ISecurityContextPort {

    @Override
    public Long userId() {
        return (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();
    }

    public String username() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null;
    }
}
