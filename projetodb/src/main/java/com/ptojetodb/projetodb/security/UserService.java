package com.ptojetodb.projetodb.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserService{

    public  Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                CustomUserDetails userDetails = (CustomUserDetails) principal;
                return userDetails.getIdUsuario();
            }
        }
        return null;
    }
}