package com.ptojetodb.projetodb.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ptojetodb.projetodb.service.AtividadeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("atividades")
@RequiredArgsConstructor
@Slf4j
public class AtividadeController implements GenericController {

    private final AtividadeService service;

    // @PostMapping
    // public ResponseEntity<Atividade> salvar(@RequestBody @Valid Atividade
    // atividade) {
    // log.info("Cadastrado nova atividade:{}", atividade.getNomeAtividade());
    // service.salvarAtividade(atividade);
    // URI location = gerarHeaderLocation(atividade.getId_atividade());
    // return ResponseEntity.created(location).build();
    // }

}
