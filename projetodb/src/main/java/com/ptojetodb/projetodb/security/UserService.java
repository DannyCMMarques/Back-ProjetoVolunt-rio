package com.ptojetodb.projetodb.security;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ptojetodb.projetodb.model.Atividade;

@Service
public class UserService {

    public Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            System.out.println("Authentication object: " + authentication);
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                CustomUserDetails userDetails = (CustomUserDetails) principal;
                System.out.println("Authenticated User ID: " + userDetails.getIdUsuario());
                return userDetails.getIdUsuario();
            } else {
                System.out.println("Principal is not CustomUserDetails: " + principal);
            }
        } else {
            System.out.println("No authentication found or authentication is not valid.");
        }
        return null;
    }

    public Optional<Atividade> findById(Long idUsuario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

}