package com.ptojetodb.projetodb.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ptojetodb.projetodb.model.Atividade;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {

        boolean existsByDataEncontroAndHorario(LocalDate dataEncontro, LocalTime horario);

        @Query("SELECT a FROM Atividade a WHERE a.usuarioCriador.idUsuario = :id OR a.usuarioConvidado.idUsuario = :id")
        Page<Atividade> filterByUsuarioCriadorOrUsuarioConvidado(@Param("id") Long id, Pageable pageable);
        
        @Query("""
                SELECT a
                FROM Atividade a
                WHERE (a.usuarioCriador.idUsuario = :id OR a.usuarioConvidado.idUsuario = :id)
                  AND (:confirmada IS NULL OR a.confirmada = :confirmada)
                  AND (:rejeitada IS NULL OR a.rejeitada = :rejeitada)
                  AND (:finalizada IS NULL OR a.finalizada = :finalizada)
            """)
        Page<Atividade> filterByAtividades(
                @Param("id") Long id,
                @Param("confirmada") Boolean confirmada,
                @Param("rejeitada") Boolean rejeitada,
                @Param("finalizada") Boolean finalizada,
                Pageable pageable);

}