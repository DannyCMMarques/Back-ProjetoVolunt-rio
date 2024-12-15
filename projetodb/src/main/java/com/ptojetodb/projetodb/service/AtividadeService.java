package com.ptojetodb.projetodb.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ptojetodb.projetodb.controller.dto.AtividadeDTO;
import com.ptojetodb.projetodb.controller.mappers.AtividadeMapper;
import com.ptojetodb.projetodb.exceptions.AtividadeNotFoundException;
import com.ptojetodb.projetodb.model.Atividade;
import com.ptojetodb.projetodb.model.Usuario;
import com.ptojetodb.projetodb.repository.AtividadeRepository;
import com.ptojetodb.projetodb.repository.UsuarioRepository;
import com.ptojetodb.projetodb.security.UserService;
import com.ptojetodb.projetodb.validator.AtividadeValidator;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtividadeService {

    private final AtividadeRepository repository;
    private final AtividadeValidator atividadeValidator;
    private final UserService userService;
    private final UsuarioRepository usuarioRepository;
    private final AtividadeMapper mapper;

    @Transactional
    public Atividade criarAtividade(Atividade atividade, Long idUsuarioCriador, Long idUsuarioConvidado) {
        Usuario usuarioCriador = usuarioRepository.findById(idUsuarioCriador)
                .orElseThrow(() -> new RuntimeException("Usuário criador não encontrado: " + idUsuarioCriador));

        Usuario usuarioConvidado = usuarioRepository.findById(idUsuarioConvidado)
                .orElseThrow(() -> new RuntimeException("Usuário convidado não encontrado: " + idUsuarioConvidado));

        atividade.setUsuarioCriador(usuarioCriador);
        atividade.setUsuarioConvidado(usuarioConvidado);
        return repository.save(atividade);
    }

    public Atividade obterAtividadeId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AtividadeNotFoundException("Atividade não encontrada para o ID: " + id));
    }

    @Transactional
    public void deletarAtividade(long id) {
        Atividade atividade = repository.findById(id)
                .orElseThrow(() -> new AtividadeNotFoundException("Atividade não encontrada para o ID: " + id));
        atividadeValidator.validar(atividade);
        repository.delete(atividade);
    }

    public List<AtividadeDTO> exibirMinhasAtividades(Long id) {
        List<Atividade> atividades = repository.filterByUsuarioCriadorOrUsuarioConvidado(id);
        return atividades.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void atualizarAtividade(Atividade atividade) {

        if (atividade.getId_atividade() == null) {
            throw new IllegalArgumentException("Para atualizar, é necessário que a atividade já tenha sido cadastrada");
        }
        atividadeValidator.validar(atividade);

        repository.save(atividade);
    }

    public List<AtividadeDTO> exibirAtividades(Long id, Boolean confirmada, Boolean rejeitada, Boolean finalizada) {
        List<Atividade> atividades = repository.filterByAtividades(id, confirmada, rejeitada, finalizada);
        return atividades.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
