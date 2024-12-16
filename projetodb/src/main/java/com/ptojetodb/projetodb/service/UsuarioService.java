package com.ptojetodb.projetodb.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ptojetodb.projetodb.model.TipoUsuario;
import com.ptojetodb.projetodb.model.Usuario;
import com.ptojetodb.projetodb.repository.UsuarioRepository;
import com.ptojetodb.projetodb.security.UserService;
import com.ptojetodb.projetodb.validator.UsuarioValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioValidator usuarioValidator;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

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

    public Optional<Usuario> findById(Long idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }

    public Page<Usuario> listarUsuariosPorTipo(TipoUsuario tipo, Pageable pageable) {
        return usuarioRepository.filterByTipoUsuario(tipo, pageable);
    }

    public Optional<Usuario> exibirUsuarioConectado() {
        Long userId = userService.getAuthenticatedUserId();
        logger.info("Buscando usuário conectado com ID: {}", userId);
        return usuarioRepository.findById(userId);
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

    public Page<Usuario> listarUsuariosPorCidade(String cidade, Pageable pageable) {
        return usuarioRepository.filterByCidade(cidade, pageable);
    }
    

}