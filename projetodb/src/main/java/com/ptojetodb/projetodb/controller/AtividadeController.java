package com.ptojetodb.projetodb.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ptojetodb.projetodb.model.Atividade;
import com.ptojetodb.projetodb.service.AtividadeService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("atividades")
@RequiredArgsConstructor
@Slf4j
public class AtividadeController implements GenericController {

    private final AtividadeService service;

    @PostMapping
    public ResponseEntity<Atividade> salvar(@RequestBody @Valid Atividade atividade) {
        log.info("Cadastrado nova atividade:{}", atividade.getNomeAtividade());
        service.salvarAtividade(atividade);
        URI location = gerarHeaderLocation(atividade.getId_atividade());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Atividade> exibirByIDAtividade(@PathVariable("id") long id) {
        Atividade atividade = service.obterAtividadeId(id);
        return ResponseEntity.ok(atividade);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarAtividade(@PathVariable("id") long id) {
        log.info("Deletando autor de ID: {} ", id);
        service.deletarAtividade(id);

        return ResponseEntity.noContent().build();
    }

}
