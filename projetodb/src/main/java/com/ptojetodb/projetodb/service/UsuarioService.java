package com.ptojetodb.projetodb.service;

import com.ptojetodb.projetodb.model.TipoUsuario;
import com.ptojetodb.projetodb.model.Usuario;
import com.ptojetodb.projetodb.repository.UsuarioRepository;
import com.ptojetodb.projetodb.validator.UsuarioValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioValidator usuarioValidator;

    public Usuario salvarUsuario(Usuario usuario) {
        usuarioValidator.validarUsuario(usuario);

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

    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> listarUsuariosPorTipo(TipoUsuario tipo){
        return usuarioRepository.filterByTipoUsuario(tipo);
    }

    public Usuario exibirPorID(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado para o ID: " + id));
    }
    
    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado para o ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado para o ID: " + id));
    
        usuarioAtualizado.setCpf(usuarioExistente.getCpf());
        usuarioAtualizado.setDataNascimento(usuarioExistente.getDataNascimento());
        usuarioAtualizado.setTipo(usuarioExistente.getTipo());
    
        usuarioExistente.setNome(usuarioAtualizado.getNome());
        usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        usuarioExistente.setSenha(usuarioAtualizado.getSenha());
        usuarioExistente.setCidade(usuarioAtualizado.getCidade());
        usuarioExistente.setEstado(usuarioAtualizado.getEstado());
        usuarioExistente.setNecessidade(usuarioAtualizado.getNecessidade());
        usuarioExistente.setHabilidade(usuarioAtualizado.getHabilidade());
        usuarioExistente.setProfissao(usuarioAtualizado.getProfissao());
    
        return usuarioRepository.save(usuarioExistente);
    }
    
    
}