package com.ptojetodb.projetodb;

import com.ptojetodb.projetodb.model.TipoUsuario;
import com.ptojetodb.projetodb.model.Usuario;
import com.ptojetodb.projetodb.repository.UsuarioRepository;
import com.ptojetodb.projetodb.security.UserService;
import com.ptojetodb.projetodb.service.UsuarioService;
import com.ptojetodb.projetodb.validator.UsuarioValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioValidator usuarioValidator;

    @Mock
    private UserService userService;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void salvarUsuario_DeveSalvarVoluntarioComHabilidade() {
        Usuario usuario = new Usuario();
        usuario.setTipo(TipoUsuario.VOLUNTARIO);
        usuario.setHabilidade("Pintura");

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.salvarUsuario(usuario);

        assertNotNull(resultado);
        verify(usuarioValidator).validarUsuario(usuario);
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void salvarUsuario_DeveLancarExcecao_QuandoVoluntarioSemHabilidade() {
        Usuario usuario = new Usuario();
        usuario.setTipo(TipoUsuario.VOLUNTARIO);
        usuario.setHabilidade(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> usuarioService.salvarUsuario(usuario));

        assertEquals("Voluntários devem ter uma habilidade.", exception.getMessage());
        verify(usuarioValidator).validarUsuario(usuario);
        verify(usuarioRepository, never()).save(usuario);
    }

    @Test
    void salvarUsuario_DeveSalvarIdosoComNecessidade() {
        Usuario usuario = new Usuario();
        usuario.setTipo(TipoUsuario.IDOSO);
        usuario.setNecessidade(List.of("Ajuda com compras"));

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.salvarUsuario(usuario);

        assertNotNull(resultado);
        assertEquals(List.of("Ajuda com compras"), resultado.getNecessidade());
        verify(usuarioValidator).validarUsuario(usuario);
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void salvarUsuario_DeveLancarExcecao_QuandoIdosoSemNecessidade() {
        Usuario usuario = new Usuario();
        usuario.setTipo(TipoUsuario.IDOSO);
        usuario.setNecessidade(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> usuarioService.salvarUsuario(usuario));

        assertEquals("Idosos devem ter uma necessidade.", exception.getMessage());
        verify(usuarioValidator).validarUsuario(usuario);
        verify(usuarioRepository, never()).save(usuario);
    }

    @Test
    void listarTodosUsuarios_DeveRetornarListaDeUsuarios() {
        List<Usuario> usuarios = List.of(new Usuario());
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> resultado = usuarioService.listarTodosUsuarios();

        assertEquals(usuarios, resultado);
        verify(usuarioRepository).findAll();
    }

    @Test
    void findById_DeveRetornarUsuarioPorId() {
        Usuario usuario = new Usuario();
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals(usuario, resultado.get());
        verify(usuarioRepository).findById(1L);
    }

    @Test
    void listarUsuariosPorTipo_DeveRetornarUsuariosFiltradosPorTipo() {
        Page<Usuario> usuarios = new PageImpl<>(List.of(new Usuario()));
        when(usuarioRepository.filterByTipoUsuario(eq(TipoUsuario.VOLUNTARIO), any(Pageable.class)))
                .thenReturn(usuarios);

        Page<Usuario> resultado = usuarioService.listarUsuariosPorTipo(TipoUsuario.VOLUNTARIO, Pageable.unpaged());

        assertEquals(usuarios, resultado);
        verify(usuarioRepository).filterByTipoUsuario(eq(TipoUsuario.VOLUNTARIO), any(Pageable.class));
    }

    @Test
    void exibirUsuarioConectado_DeveRetornarUsuarioConectado() {
        Usuario usuario = new Usuario();
        when(userService.getAuthenticatedUserId()).thenReturn(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.exibirUsuarioConectado();

        assertTrue(resultado.isPresent());
        assertEquals(usuario, resultado.get());
        verify(userService).getAuthenticatedUserId();
        verify(usuarioRepository).findById(1L);
    }

    @Test
    void exibirPorID_DeveRetornarUsuarioPorId() {
        Usuario usuario = new Usuario();
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.exibirPorID(1L);

        assertEquals(usuario, resultado);
        verify(usuarioRepository).findById(1L);
    }

    @Test
    void exibirPorID_DeveLancarExcecao_QuandoUsuarioNaoExistir() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> usuarioService.exibirPorID(1L));

        assertEquals("Usuário não encontrado para o ID: 1", exception.getMessage());
        verify(usuarioRepository).findById(1L);
    }

    @Test
    void deletarUsuario_DeveDeletarUsuario() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);

        usuarioService.deletarUsuario(1L);

        verify(usuarioRepository).existsById(1L);
        verify(usuarioRepository).deleteById(1L);
    }

    @Test
    void deletarUsuario_DeveLancarExcecao_QuandoUsuarioNaoExistir() {
        when(usuarioRepository.existsById(1L)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> usuarioService.deletarUsuario(1L));

        assertEquals("Usuário não encontrado para o ID: 1", exception.getMessage());
        verify(usuarioRepository).existsById(1L);
        verify(usuarioRepository, never()).deleteById(1L);
    }

    @Test
    void atualizarUsuario_DeveAtualizarUsuario() {
        Usuario usuarioExistente = new Usuario();
        Usuario usuarioAtualizado = new Usuario();

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(usuarioExistente)).thenReturn(usuarioExistente);

        Usuario resultado = usuarioService.atualizarUsuario(1L, usuarioAtualizado);

        assertNotNull(resultado);
        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository).save(usuarioExistente);
    }

    @Test
    void atualizarUsuario_DeveLancarExcecao_QuandoUsuarioNaoExistir() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> usuarioService.atualizarUsuario(1L, new Usuario()));

        assertEquals("Usuário não encontrado para o ID: 1", exception.getMessage());
        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void listarUsuariosPorCidade_DeveRetornarUsuariosFiltradosPorCidade() {
        Page<Usuario> usuarios = new PageImpl<>(Collections.singletonList(new Usuario()));
        when(usuarioRepository.filterByCidade(eq("São Paulo"), any(Pageable.class))).thenReturn(usuarios);

        Page<Usuario> resultado = usuarioService.listarUsuariosPorCidade("São Paulo", Pageable.unpaged());

        assertEquals(usuarios, resultado);
        verify(usuarioRepository).filterByCidade(eq("São Paulo"), any(Pageable.class));
    }
}
