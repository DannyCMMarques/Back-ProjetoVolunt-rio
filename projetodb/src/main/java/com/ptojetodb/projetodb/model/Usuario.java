package com.ptojetodb.projetodb.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    // @CPF
    private String cpf;
    private String nome;
    private LocalDate dataNascimento;
    private String email;
    private String senha;
    private String cidade;
    private String estado;

    @ElementCollection
    private List<String> necessidade;

    private String habilidade;
    private String profissao;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;
    private String foto;
}
