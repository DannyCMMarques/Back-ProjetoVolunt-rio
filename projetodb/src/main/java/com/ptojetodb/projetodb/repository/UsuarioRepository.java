package com.ptojetodb.projetodb.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ptojetodb.projetodb.model.TipoUsuario;
import com.ptojetodb.projetodb.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u WHERE u.tipo= :tipo")
    Page<Usuario> filterByTipoUsuario(@Param("tipo") TipoUsuario tipo, Pageable pageable);

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Optional<Usuario> findByEmail(@Param("email") String email);

    public boolean existsByCpf(String cpf);

    public boolean existsByEmail(String email);

    public Optional<Usuario> findById(Long id);

}
