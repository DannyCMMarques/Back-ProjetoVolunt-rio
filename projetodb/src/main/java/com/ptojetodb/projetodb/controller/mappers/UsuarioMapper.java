package com.ptojetodb.projetodb.controller.mappers;

import org.mapstruct.Mapper;

import com.ptojetodb.projetodb.controller.dto.UsuarioDTO;
import com.ptojetodb.projetodb.model.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);

    UsuarioDTO toDTO(Usuario usuario);
}
