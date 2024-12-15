package com.ptojetodb.projetodb.controller.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ptojetodb.projetodb.controller.dto.AtividadeDTO;
import com.ptojetodb.projetodb.model.Atividade;

@Mapper(componentModel = "spring", uses = UsuarioMapper.class)
public interface AtividadeMapper {

    @Mapping(source = "usuarioCriador", target = "usuarioCriador")
    @Mapping(source = "usuarioConvidado", target = "usuarioConvidado")
    AtividadeDTO toDTO(Atividade atividade);

    @Mapping(source = "usuarioCriador", target = "usuarioCriador")
    @Mapping(source = "usuarioConvidado", target = "usuarioConvidado")
    Atividade toEntity(AtividadeDTO dto);
}
