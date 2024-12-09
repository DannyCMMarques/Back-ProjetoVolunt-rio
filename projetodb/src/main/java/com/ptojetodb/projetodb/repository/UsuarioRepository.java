package com.ptojetodb.projetodb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ptojetodb.projetodb.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}