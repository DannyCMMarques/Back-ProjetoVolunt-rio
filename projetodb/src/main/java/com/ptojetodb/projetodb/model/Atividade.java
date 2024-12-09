package com.ptojetodb.projetodb.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

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
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "interações")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Atividade {

    @Id
    @Column(name = "id_atividade")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_atividade;

    @NotBlank(message = "Campo Obrigatório")
    private String nomeAtividade;

    @NotBlank
    @Column(name = "descricao_atividade")
    private String descricaoAtividade;

    @NotBlank(message = "Campo Obrigatório")
    private LocalDate data_Encontro;

    @NotBlank(message = "Campo Obrigatório")
    private LocalTime horario;

    @NotBlank(message = "Campo Obrigatório")
    @Enumerated(EnumType.STRING)
    private TipoEncontro tipoEncontro;

    @NotBlank(message = "Campo Obrigatório")
    private String endereco;

    @NotBlank(message = "Campo Obrigatório")
    private Boolean confirmacao;

    @NotBlank(message = "Campo Obrigatório")
    private Boolean rejeitado;

    @NotBlank(message = "Campo Obrigatório")
    private Boolean finalizada;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

}
