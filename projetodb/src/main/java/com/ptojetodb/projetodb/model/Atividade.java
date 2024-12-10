package com.ptojetodb.projetodb.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "interações")

@EntityListeners(AuditingEntityListener.class)
@Data
public class Atividade {

    @Id
    @Column(name = "id_atividade")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_atividade;

    private String nomeAtividade;

    @NotBlank
    @Column(name = "descricao_atividade")
    private String descricaoAtividade;

    private LocalDate dataEncontro;

    private LocalTime horario;

    @Enumerated(EnumType.STRING)
    private TipoEncontro tipoEncontro;

    private String endereco;

    private Boolean confirmada;

    private Boolean rejeitada;

    private Boolean finalizada;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @OneToOne
    @JoinColumn(name = "id_usuario_criador")
    private Usuario usuarioCriador;

    @OneToOne
    @JoinColumn(name = "id_usuario_convidado")
    private Usuario usuarioConvidado;
}
