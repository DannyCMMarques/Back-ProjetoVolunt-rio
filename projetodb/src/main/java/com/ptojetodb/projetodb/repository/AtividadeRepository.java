package com.ptojetodb.projetodb.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ptojetodb.projetodb.model.Atividade;

public interface AtividadeRepository extends JpaRepository<Atividade, UUID> {

}
