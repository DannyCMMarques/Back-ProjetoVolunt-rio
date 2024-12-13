package com.ptojetodb.projetodb.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ptojetodb.projetodb.model.Usuario;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final Usuario usuario;

    public Long getIdUsuario() {
        return usuario.getIdUsuario(); // Retorna o ID do usuário
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return usuario.getSenha(); // Retorna a senha do usuário
    }

    @Override
    public String getUsername() {
        return usuario.getEmail(); // Retorna o email como username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Customize conforme sua lógica
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Customize conforme sua lógica
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Customize conforme sua lógica
    }

    @Override
    public boolean isEnabled() {
        return true; // Customize conforme sua lógica
    }
}