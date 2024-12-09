package com.ptojetodb.projetodb.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String cpf;
    private String nome;
    private String email;
    private String senha;
    private String grupo; 
    private String cidade;
    private String estado;
    private String necessidade; 
    private String habilidade; 
}



