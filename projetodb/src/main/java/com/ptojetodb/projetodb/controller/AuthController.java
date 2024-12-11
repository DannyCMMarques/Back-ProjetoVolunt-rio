package com.ptojetodb.projetodb.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ptojetodb.projetodb.model.Usuario;
import com.ptojetodb.projetodb.repository.UsuarioRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UsuarioRepository usarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {

        if (usuario.getEmail() == null || usuario.getSenha() == null) {
            return ResponseEntity.badRequest().body("Email e senha são obrigatórios");
        }

        Optional<Usuario> existeUser = usarioRepository.findByEmail(usuario.getEmail());
        if (existeUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário ou senha inválidos");
        }
        boolean passwordHash = passwordEncoder.matches(usuario.getSenha(), existeUser.get().getSenha());
        if (!passwordHash) {
            return ResponseEntity.badRequest().body("Usuário ou senha inválidos");
        }
        return ResponseEntity.ok("Login realizado com sucesso");
    }
}
