package com.ptojetodb.projetodb.controller;

import java.net.URI;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public interface GenericController {

    default URI gerarHeaderLocation(long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}