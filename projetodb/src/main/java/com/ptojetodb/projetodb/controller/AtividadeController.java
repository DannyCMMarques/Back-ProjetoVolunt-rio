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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;

import com.ptojetodb.projetodb.controller.dto.AtividadeDTO;
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

        if (atividade.getUsuarioCriador() == null || atividade.getUsuarioCriador().getIdUsuario() == null) {
            throw new IllegalArgumentException("Usuário criador é obrigatório.");
        }
        if (atividade.getUsuarioConvidado() == null || atividade.getUsuarioConvidado().getIdUsuario() == null) {
            throw new IllegalArgumentException("Usuário convidado é obrigatório.");
        }

        Long idUsuarioCriador = atividade.getUsuarioCriador().getIdUsuario();
        Long idUsuarioConvidado = atividade.getUsuarioConvidado().getIdUsuario();

        Atividade novaAtividade = service.criarAtividade(atividade, idUsuarioCriador, idUsuarioConvidado);

        URI location = gerarHeaderLocation(novaAtividade.getId_atividade());
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
        service.atualizarAtividade(atividade);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/minhas-atividades/{id}")
    public ResponseEntity<Page<AtividadeDTO>> exibirAtividades(
            @PathVariable Long id,
            @RequestParam(required = false) Boolean confirmada,
            @RequestParam(required = false) Boolean rejeitada,
            @RequestParam(required = false) Boolean finalizada,
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "10") int size 
    ) {
        Page<AtividadeDTO> atividades = service.exibirAtividades(id, confirmada, rejeitada, finalizada, page, size);
        return ResponseEntity.ok(atividades);
    }
    

}
