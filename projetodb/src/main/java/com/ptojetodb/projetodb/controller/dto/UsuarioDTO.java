package com.ptojetodb.projetodb.controller.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO(

    Long idUsuario,
    @NotBlank(message = "campo obrigatorio")
    String nome,

    @NotBlank(message = "campo obrigatorio")
    LocalDate dataNascimento,

    @NotBlank(message = "campo obrigatorio")
    String cidade,

    @NotBlank(message = "campo obrigatorio")
    String estado,
    String necessidade,
    String habilidade,
    String profissao
) {
}
