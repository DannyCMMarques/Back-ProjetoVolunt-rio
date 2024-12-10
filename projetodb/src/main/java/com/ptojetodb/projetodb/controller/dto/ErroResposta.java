package com.ptojetodb.projetodb.controller.dto;

import java.util.List;

import org.springframework.http.HttpStatus;

public record ErroResposta(int status, String mensagem, List<ErroCampo> erros) {

    public static ErroResposta respostaPadrao(String mensagem) {
        return new ErroResposta(HttpStatus.BAD_REQUEST.value(), mensagem, List.of());
    }

    public static ErroResposta conflito(String mensagem) {
        return new ErroResposta(HttpStatus.CONFLICT.value(), mensagem, List.of());
    }

}
