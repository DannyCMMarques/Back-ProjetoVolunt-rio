package com.ptojetodb.projetodb.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String cpf;
    private String nome;
    private LocalDate dataNascimento; 
    private String email;
    private String senha;
    private String cidade;
    private String estado;
    private String necessidade; 
    private String habilidade;  
    private String profissao;   

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;   
}