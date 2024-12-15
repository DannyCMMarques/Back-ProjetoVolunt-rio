package com.ptojetodb.projetodb.controller.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.ptojetodb.projetodb.model.TipoEncontro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtividadeDTO {
    private Long idAtividade;
    private String nomeAtividade;
    private String descricaoAtividade;
    private LocalDate dataEncontro;
    private LocalTime horario;
    private TipoEncontro tipoEncontro;
    private String endereco;
    private Boolean confirmada;
    private Boolean rejeitada;
    private Boolean finalizada;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
    private UsuarioDTO usuarioCriador;
    private UsuarioDTO usuarioConvidado;
}
