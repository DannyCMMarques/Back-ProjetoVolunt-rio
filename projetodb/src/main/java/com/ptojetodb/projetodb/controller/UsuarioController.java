package com.ptojetodb.projetodb.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ptojetodb.projetodb.controller.dto.UsuarioDTO;
import com.ptojetodb.projetodb.controller.mappers.UsuarioMapper;
import com.ptojetodb.projetodb.model.Atividade;
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
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios(@RequestParam TipoUsuario tipo) {
        List<Usuario> usuarios = usuarioService.listarUsuariosPorTipo(tipo);
        
        List<UsuarioDTO> usuariosDto = usuarios.stream()
                .map(usuario -> usuarioMapper.toDTO(usuario)) 
                .toList();

        return ResponseEntity.ok(usuariosDto);
    }

    @GetMapping("{id}")
    public ResponseEntity<Usuario> exibirPorID(@PathVariable Long id){
    Usuario usuario = usuarioService.exibirPorID(id);
    return ResponseEntity.ok(usuario);
    }
}
