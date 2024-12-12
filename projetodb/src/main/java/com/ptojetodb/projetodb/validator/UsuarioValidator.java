package com.ptojetodb.projetodb.validator;

import org.springframework.stereotype.Component;

import com.ptojetodb.projetodb.model.Usuario;
import com.ptojetodb.projetodb.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsuarioValidator {

    private final UsuarioRepository usuarioRepository;

    public void validarUsuario(Usuario usuario) {
        if (cpfDuplicado(usuario.getCpf())) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com este CPF: " + usuario.getCpf());
        }

        if (emailDuplicado(usuario.getEmail())) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com este email: " + usuario.getEmail());
        }
    }

    private boolean cpfDuplicado(String cpf) {
        return usuarioRepository.existsByCpf(cpf);
    }

    private boolean emailDuplicado(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}

