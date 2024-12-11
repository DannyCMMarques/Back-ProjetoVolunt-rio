package com.ptojetodb.projetodb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ptojetodb.projetodb.exceptions.AtividadeNotFoundException;
import com.ptojetodb.projetodb.model.Atividade;
import com.ptojetodb.projetodb.repository.AtividadeRepository;
import com.ptojetodb.projetodb.validator.AtividadeValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtividadeService {

    private final AtividadeRepository repository;
    private final AtividadeValidator atividadeValidator;

    public Atividade salvarAtividade(Atividade atividade) {
        atividadeValidator.validar(atividade);
        // TODO: é necessário ainda construir essa parte para que meu usuario criador
        // sempre seja quem ta conectado
        // Usuario usuario = securityService.obterUsuarioLogado();
        // atividade.setUsuarioCriador(usuario);
        return repository.save(atividade);

    }

    public Atividade obterAtividadeId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AtividadeNotFoundException("Atividade não encontrada para o ID: " + id));
    }

    public void deletarAtividade(long id) {
        Atividade atividade = repository.findById(id)
                .orElseThrow(() -> new AtividadeNotFoundException("Atividade não encontrada para o ID: " + id));

        atividadeValidator.validar(atividade);
        repository.delete(atividade);
    }

    public List<Atividade> exibirMinhasAtividades(Long id) {
        return repository.filterByUsuarioCriadorOrUsuarioConvidado(id);
    }

    public void atualizarAtividade(Atividade atividade) {

        if (atividade.getId_atividade() == null) {
            throw new IllegalArgumentException("Para atualizar, é necessário que a atividade já tenha sido cadastrada");
        }
        atividadeValidator.validar(atividade);

        repository.save(atividade);
    }

    public List<Atividade> exibirAtividadesPendentes(long id) {
        return repository.filterByAtividadePendente(id);
    }

    public List<Atividade> exibirAtividadesRejeitadas(long id) {
        return repository.filterByAtividadeRejeitada(id);
    }

    public List<Atividade> exibirAtividadesConfirmadas(long id) {
        return repository.filterByAtividadeconfirmada(id);
    }

    public List<Atividade> exibirAtividadesFinalizadas(long id) {
        return repository.filterByAtividadeFinalizada(id);
    }
}
