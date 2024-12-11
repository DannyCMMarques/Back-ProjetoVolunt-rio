package com.ptojetodb.projetodb.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ptojetodb.projetodb.model.Atividade;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {

    // para usar para validação
    boolean existsByDataEncontroAndHorario(LocalDate dataEncontro, LocalTime horario);

    @Query("SELECT a FROM Atividade a WHERE a.usuarioCriador.id = :id OR a.usuarioConvidado.id = :id")
    List<Atividade> filterByUsuarioCriadorOrUsuarioConvidado(@Param("id") Long id);

    @Query("SELECT a FROM Atividade a WHERE (a.usuarioCriador.id = :id OR a.usuarioConvidado.id = :id) AND a.confirmacao = false AND a.rejeitado = false")
    List<Atividade> filterByAtividadePendente(@Param("id") Long id);

    @Query("SELECT a FROM Atividade a WHERE (a.usuarioCriador.id = :id OR a.usuarioConvidado.id = :id) AND a.confirmacao = false AND a.rejeitado = true")
    List<Atividade> filterByAtividadeRejeitada(@Param("id") Long id);

    @Query("SELECT a FROM Atividade a WHERE (a.usuarioCriador.id = :id OR a.usuarioConvidado.id = :id) AND a.confirmacao = true ")
    List<Atividade> filterByAtividadeConfirmacao(@Param("id") Long id);

    @Query("SELECT a FROM Atividade a WHERE (a.usuarioCriador.id = :id OR a.usuarioConvidado.id = :id) AND a.finalizada = true ")
    List<Atividade> filterByAtividadeFinalizada(@Param("id") Long id);

}
