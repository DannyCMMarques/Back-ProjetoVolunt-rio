package com.ptojetodb.projetodb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ptojetodb.projetodb.controller.dto.UsuarioDTO;
import com.ptojetodb.projetodb.controller.mappers.UsuarioMapper;
import com.ptojetodb.projetodb.model.TipoUsuario;
import com.ptojetodb.projetodb.model.Usuario;
import com.ptojetodb.projetodb.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioMapper usuarioMapper;
    private final UsuarioService usuarioService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping()
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
        if (usuario.getTipo() != TipoUsuario.IDOSO && usuario.getTipo() != TipoUsuario.VOLUNTARIO) {
            return ResponseEntity.badRequest().body(null);
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuario novoUsuario = usuarioService.salvarUsuario(usuario);
        return ResponseEntity.ok(novoUsuario);
    }

    @GetMapping()
    public ResponseEntity<Page<UsuarioDTO>> listarPorTipo(
            @RequestParam TipoUsuario tipo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Usuario> usuarios = usuarioService.listarUsuariosPorTipo(tipo, PageRequest.of(page, size));
        Page<UsuarioDTO> usuariosDto = usuarios.map(usuarioMapper::toDTO);
        return ResponseEntity.ok(usuariosDto);
    }

    @GetMapping("{id}")
    public ResponseEntity<Usuario> exibirPorID(@PathVariable Long id) {
        Usuario usuario = usuarioService.exibirPorID(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/listaUsuario")
    public ResponseEntity<Usuario> exibirUsuario() {
        Optional<Usuario> optionalUsuario = usuarioService.exibirUsuarioConectado();
        if (optionalUsuario.isPresent()) {
            return ResponseEntity.ok(optionalUsuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Usuario>> listarTodosUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(
            @PathVariable Long id,
            @RequestBody Usuario usuarioAtualizado) {
        Usuario usuario = usuarioService.atualizarUsuario(id, usuarioAtualizado);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping(params = "cidade")
    public ResponseEntity<Page<UsuarioDTO>> listarPorCidade(
            @RequestParam String cidade,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
    
        Page<Usuario> usuarios = usuarioService.listarUsuariosPorCidade(cidade, PageRequest.of(page, size));
        Page<UsuarioDTO> usuariosDto = usuarios.map(usuarioMapper::toDTO);
        return ResponseEntity.ok(usuariosDto);
    }


}
