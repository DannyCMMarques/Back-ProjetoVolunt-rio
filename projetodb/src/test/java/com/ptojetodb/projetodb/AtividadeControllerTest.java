package com.ptojetodb.projetodb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.ptojetodb.projetodb.repository.UsuarioRepository;
import com.ptojetodb.projetodb.repository.AtividadeRepository;
import com.ptojetodb.projetodb.model.Usuario;
import com.ptojetodb.projetodb.model.Atividade;
import com.ptojetodb.projetodb.model.TipoEncontro;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AtividadeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AtividadeRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;



    @Test
    void deveExibirAtividadePorId() throws Exception {
       
        Usuario usuarioCriador = new Usuario();
        usuarioCriador.setNome("Criador");
        usuarioCriador.setEmail("criador@example.com");
        usuarioCriador = usuarioRepository.save(usuarioCriador);

        Usuario usuarioConvidado = new Usuario();
        usuarioConvidado.setNome("Convidado");
        usuarioConvidado.setEmail("convidado@example.com");
        usuarioConvidado = usuarioRepository.save(usuarioConvidado);

       
        Atividade atividade = new Atividade();
        atividade.setNomeAtividade("Reunião");
        atividade.setDescricaoAtividade("Reunião de alinhamento");
        atividade.setDataEncontro(LocalDate.of(2024, 11, 20));  
        atividade.setHorario(LocalTime.of(10, 0)); 
        atividade.setTipoEncontro(TipoEncontro.ONLINE);  
        atividade.setUsuarioCriador(usuarioCriador);
        atividade.setUsuarioConvidado(usuarioConvidado);
        atividade = repository.save(atividade);


        mockMvc.perform(get("/atividades/" + atividade.getId_atividade()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nomeAtividade").value("Reunião"));
    }

 
}
