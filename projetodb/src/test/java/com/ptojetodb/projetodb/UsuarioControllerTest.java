package com.ptojetodb.projetodb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import com.ptojetodb.projetodb.controller.UsuarioController;
import com.ptojetodb.projetodb.controller.dto.UsuarioDTO;
import com.ptojetodb.projetodb.controller.mappers.UsuarioMapper;
import com.ptojetodb.projetodb.model.TipoUsuario;
import com.ptojetodb.projetodb.model.Usuario;
import com.ptojetodb.projetodb.service.UsuarioService;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @Test
    void deveCadastrarUsuarioComSucesso() {

        Usuario usuario = new Usuario();
        usuario.setNome("Teste");
        usuario.setSenha("1234");
        usuario.setTipo(TipoUsuario.IDOSO);

        Usuario usuarioSalvo = new Usuario();
        usuarioSalvo.setNome("Teste");
        usuarioSalvo.setSenha("senhaCodificada");
        usuarioSalvo.setTipo(TipoUsuario.IDOSO);

        when(usuarioService.salvarUsuario(any(Usuario.class))).thenReturn(usuarioSalvo);

        ResponseEntity<Usuario> resposta = usuarioController.cadastrarUsuario(usuario);

        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals("Teste", resposta.getBody().getNome());
        assertEquals(TipoUsuario.IDOSO, resposta.getBody().getTipo());
        verify(usuarioService, times(1)).salvarUsuario(any(Usuario.class));
    }

    @Test
    void naoDeveCadastrarUsuarioComTipoInvalido() {

        Usuario usuario = new Usuario();
        usuario.setNome("Teste");
        usuario.setSenha("1234");
        usuario.setTipo(null);

        ResponseEntity<Usuario> resposta = usuarioController.cadastrarUsuario(usuario);

        assertEquals(400, resposta.getStatusCodeValue());
        verify(usuarioService, never()).salvarUsuario(any(Usuario.class));
    }

    @Test
    void deveListarUsuariosPorTipo() {

        TipoUsuario tipo = TipoUsuario.VOLUNTARIO;
        Page<Usuario> usuariosPage = mock(Page.class);
        when(usuarioService.listarUsuariosPorTipo(eq(tipo), any(PageRequest.class))).thenReturn(usuariosPage);
        when(usuariosPage.map(any())).thenReturn(mock(Page.class)); 

        ResponseEntity<Page<UsuarioDTO>> resposta = usuarioController.listarPorTipo(tipo, 0, 10);

        assertEquals(200, resposta.getStatusCodeValue());
        verify(usuarioService, times(1)).listarUsuariosPorTipo(eq(tipo), any(PageRequest.class));
    }

    @Test
    void deveExibirUsuarioPorId() {

        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(id);
        when(usuarioService.exibirPorID(id)).thenReturn(usuario);

        ResponseEntity<Usuario> resposta = usuarioController.exibirPorID(id);

        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals(id, resposta.getBody().getIdUsuario());
        verify(usuarioService, times(1)).exibirPorID(id);
    }

    @Test
    void deveRetornarNotFoundSeUsuarioNaoExistirPorId() {

        Long id = 999L;
        when(usuarioService.exibirPorID(id)).thenReturn(null);

        ResponseEntity<Usuario> resposta = usuarioController.exibirPorID(id);

        assertEquals(404, resposta.getStatusCodeValue());
        verify(usuarioService, times(1)).exibirPorID(id);
    }

    @Test
    void deveExibirUsuarioConectado() {

        Usuario usuario = new Usuario();
        usuario.setNome("Usuario Conectado");
        when(usuarioService.exibirUsuarioConectado()).thenReturn(Optional.of(usuario));

        ResponseEntity<Usuario> resposta = usuarioController.exibirUsuario();

        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals("Usuario Conectado", resposta.getBody().getNome());
        verify(usuarioService, times(1)).exibirUsuarioConectado();
    }

    @Test
    void deveRetornarNotFoundSeUsuarioConectadoNaoExistir() {

        when(usuarioService.exibirUsuarioConectado()).thenReturn(Optional.empty());

        ResponseEntity<Usuario> resposta = usuarioController.exibirUsuario();

        assertEquals(404, resposta.getStatusCodeValue());
        verify(usuarioService, times(1)).exibirUsuarioConectado();
    }

    @Test
    void deveListarTodosUsuarios() {

        List<Usuario> usuarios = List.of(new Usuario(), new Usuario());
        when(usuarioService.listarTodosUsuarios()).thenReturn(usuarios);

        ResponseEntity<List<Usuario>> resposta = usuarioController.listarTodosUsuarios();

        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals(2, resposta.getBody().size());
        verify(usuarioService, times(1)).listarTodosUsuarios();
    }

    @Test
    void deveDeletarUsuario() {

        Long id = 1L;

        ResponseEntity<Void> resposta = usuarioController.deletarUsuario(id);

        assertEquals(204, resposta.getStatusCodeValue());
        verify(usuarioService, times(1)).deletarUsuario(id);
    }

    @Test
    void deveAtualizarUsuario() {

        Long id = 1L;
        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setNome("Atualizado");

        Usuario usuarioSalvo = new Usuario();
        usuarioSalvo.setNome("Atualizado");

        when(usuarioService.atualizarUsuario(id, usuarioAtualizado)).thenReturn(usuarioSalvo);

        ResponseEntity<Usuario> resposta = usuarioController.atualizarUsuario(id, usuarioAtualizado);

        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals("Atualizado", resposta.getBody().getNome());
        verify(usuarioService, times(1)).atualizarUsuario(id, usuarioAtualizado);
    }

    @Test
    void deveListarUsuariosPorCidade() {

        String cidade = "São Paulo";
        Page<Usuario> usuariosPage = mock(Page.class);
        when(usuarioService.listarUsuariosPorCidade(eq(cidade), any(PageRequest.class))).thenReturn(usuariosPage);
        when(usuariosPage.map(any())).thenReturn(mock(Page.class));

        ResponseEntity<Page<UsuarioDTO>> resposta = usuarioController.listarPorCidade(cidade, 0, 10);

        assertEquals(200, resposta.getStatusCodeValue());
        verify(usuarioService, times(1)).listarUsuariosPorCidade(eq(cidade), any(PageRequest.class));
    }

}
