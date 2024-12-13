package com.ptojetodb.projetodb.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ptojetodb.projetodb.model.Usuario;
import com.ptojetodb.projetodb.repository.UsuarioRepository;
import com.ptojetodb.projetodb.security.CustomUserDetails;
import com.ptojetodb.projetodb.security.JwtUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        if (usuario.getEmail() == null || usuario.getSenha() == null) {
            return ResponseEntity.badRequest().body("Email e senha são obrigatórios");
        }

        Optional<Usuario> existeUser = usuarioRepository.findByEmail(usuario.getEmail());
        if (existeUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário ou senha inválidos");
        }

        boolean passwordHash = passwordEncoder.matches(usuario.getSenha(), existeUser.get().getSenha());
        if (!passwordHash) {
            return ResponseEntity.badRequest().body("Usuário ou senha inválidos");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuario.getEmail(),
                        usuario.getSenha()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtUtils.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(token);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return ResponseEntity.ok(userDetails);
        }
        return ResponseEntity.badRequest().body("Usuário não autenticado");
    }
}