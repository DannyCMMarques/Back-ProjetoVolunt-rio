package com.ptojetodb.projetodb.controller;

import com.ptojetodb.projetodb.model.TipoUsuario;
import com.ptojetodb.projetodb.model.Usuario;
import com.ptojetodb.projetodb.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @PostMapping("/idoso")
    public ResponseEntity<Usuario> cadastrarIdoso(@RequestBody Usuario usuario) {
        if (usuario.getTipo() != TipoUsuario.IDOSO) {
            return ResponseEntity.badRequest().body(null);
        }
        Usuario novoUsuario = usuarioService.salvarUsuario(usuario);
        return ResponseEntity.ok(novoUsuario);
    }

    
    @PostMapping("/voluntario")
    public ResponseEntity<Usuario> cadastrarVoluntario(@RequestBody Usuario usuario) {
        if (usuario.getTipo() != TipoUsuario.VOLUNTARIO) {
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


