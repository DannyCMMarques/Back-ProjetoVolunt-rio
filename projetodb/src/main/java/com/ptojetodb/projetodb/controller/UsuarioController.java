package com.ptojetodb.projetodb.controller;

import com.ptojetodb.projetodb.model.Usuario;
import com.ptojetodb.projetodb.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    

 
    @PostMapping("/idoso")
    public ResponseEntity<Usuario> cadastrarIdoso(@RequestBody Usuario idoso) {
        if (idoso.getGrupo() == null || !idoso.getGrupo().equalsIgnoreCase("idoso")) {
            idoso.setGrupo("idoso");
        }
        Usuario novoIdoso = usuarioRepository.save(idoso);
        return ResponseEntity.ok(novoIdoso);
    }

  
    @PostMapping("/voluntario")
    public ResponseEntity<Usuario> cadastrarVoluntario(@RequestBody Usuario voluntario) {
        if (voluntario.getGrupo() == null || !voluntario.getGrupo().equalsIgnoreCase("voluntario")) {
            voluntario.setGrupo("voluntario");
        }
        Usuario novoVoluntario = usuarioRepository.save(voluntario);
        return ResponseEntity.ok(novoVoluntario);
    }


    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }
}

