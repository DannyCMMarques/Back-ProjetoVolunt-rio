package com.ptojetodb.projetodb.controller;

import com.ptojetodb.projetodb.model.TipoUsuario;
import com.ptojetodb.projetodb.model.Usuario;
import com.ptojetodb.projetodb.service.UsuarioService;

import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("usuarios")
public class UsuarioController {

    
    private final UsuarioService usuarioService;


    @PostMapping()
    public ResponseEntity<Usuario> cadastrarIdoso(@RequestBody Usuario usuario) {
        if (usuario.getTipo() != TipoUsuario.IDOSO) {
            return ResponseEntity.badRequest().body(null);
        }
        Usuario novoUsuario = usuarioService.salvarUsuario(usuario);
        return ResponseEntity.ok(novoUsuario);
    }

    
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }
}


