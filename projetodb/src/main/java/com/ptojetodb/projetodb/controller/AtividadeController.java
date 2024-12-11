package com.ptojetodb.projetodb.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ptojetodb.projetodb.model.Atividade;
import com.ptojetodb.projetodb.service.AtividadeService;

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

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable("id") long id, @RequestBody @Valid Atividade atividade) {
        Atividade atividadeSelecionada = service.obterAtividadeId(id);

        atividade.setDataEncontro(atividade.getDataEncontro());
        atividade.setDescricaoAtividade(atividade.getDescricaoAtividade());
        atividade.setEndereco(atividade.getEndereco());
        atividade.setUsuarioConvidado(atividade.getUsuarioConvidado());
        atividade.setNomeAtividade(atividade.getNomeAtividade());
        // atividade.getHorario(atividade.getHorario());
        service.atualizarAtividade(atividade);
        return ResponseEntity.noContent().build();
    }

    // TODO: Depois trocar por questao de privacidade do usuario e refatorar
    @GetMapping("minhas-atividades/{id}")
    public ResponseEntity<List<Atividade>> exibirMinhasAtividades(@PathVariable("id") long id) {
        List<Atividade> atividade = service.exibirMinhasAtividades(id);
        return ResponseEntity.ok(atividade);
    }

    @GetMapping("pendentes/{id}")
    public ResponseEntity<List<Atividade>> exibirAtividadesPendentes(@PathVariable("id") long id) {
        List<Atividade> atividade = service.exibirAtividadesPendentes(id);
        return ResponseEntity.ok(atividade);
    }

    @GetMapping("rejeitadas/{id}")
    public ResponseEntity<List<Atividade>> exibirAtividadesRejeitadas(@PathVariable("id") long id) {
        List<Atividade> atividade = service.exibirAtividadesRejeitadas(id);
        return ResponseEntity.ok(atividade);
    }

    @GetMapping("confirmadas/{id}")
    public ResponseEntity<List<Atividade>> exibirAtividadesConfirmadas(@PathVariable("id") long id) {
        List<Atividade> atividade = service.exibirAtividadesConfirmadas(id);
        return ResponseEntity.ok(atividade);
    }

    @GetMapping("finalizadas/{id}")
    public ResponseEntity<List<Atividade>> exibirAtividadesFinalizadas(@PathVariable("id") long id) {
        List<Atividade> atividade = service.exibirAtividadesFinalizadas(id);
        return ResponseEntity.ok(atividade);
    }

}
