package com.ptojetodb.projetodb.service;

import com.ptojetodb.projetodb.model.TipoUsuario;
import com.ptojetodb.projetodb.model.Usuario;
import com.ptojetodb.projetodb.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario salvarUsuario(Usuario usuario) {
        if (usuario.getTipo() == TipoUsuario.VOLUNTARIO) {
            if (usuario.getHabilidade() == null || usuario.getHabilidade().isEmpty()) {
                throw new IllegalArgumentException("Voluntários devem ter uma habilidade.");
            }
        } else if (usuario.getTipo() == TipoUsuario.IDOSO) {
            if (usuario.getNecessidade() == null || usuario.getNecessidade().isEmpty()) {
                throw new IllegalArgumentException("Idosos devem ter uma necessidade.");
            }
        }
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> listarIdosos(){
        return usuarioRepository.filterByTipoUsuario(TipoUsuario.IDOSO);
    }
}