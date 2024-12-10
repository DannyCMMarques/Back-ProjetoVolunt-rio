package com.ptojetodb.projetodb.validator;

import org.springframework.stereotype.Component;

import com.ptojetodb.projetodb.exceptions.OperacaoNaoPermitidaException;
import com.ptojetodb.projetodb.exceptions.RegistroDuplicadoException;
import com.ptojetodb.projetodb.model.Atividade;
import com.ptojetodb.projetodb.repository.AtividadeRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AtividadeValidator {

    private final AtividadeRepository repository;
    private final AtividadeValidator atividadeValidator;

    public void validar(Atividade atividade) {
        if (existeAtividadeAgendada(atividade)) {
            throw new RegistroDuplicadoException("Você já possui atividade agendada nesse horário");
        }

         if(possoEditar(atividade) || possoExcluir(atividade) == false){
              throw new OperacaoNaoPermitidaException("Você não pode editar essa atividade");
         }
    }

     private boolean existeAtividadeAgendada(Atividade atividade) {
         return repository.existsByDataEncontroAndHorario(atividade.getDataEncontro(),atividade.getHorario());
    }

    private boolean possoEditar(Atividade atividade) {
        if (atividade.getConfirmacao() & atividade.getRejeitado() & atividade.getFinalizada() != true) {
            return true;
        }
        return false;
    }

    private boolean possoExcluir(Atividade atividade) {
        if (atividade.getFinalizada() == false) {
            return true;
        }
        return false;
    }
}
