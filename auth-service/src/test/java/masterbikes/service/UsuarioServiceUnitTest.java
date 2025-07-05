package masterbikes.service;

import masterbikes.model.Usuario;
import masterbikes.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioServiceUnitTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    public UsuarioServiceUnitTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerUsuario_llamaAlRepositorioYDevuelveUsuario() {
        Usuario usuario = Usuario.builder().id(1L).email("test@email.com").build();
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> result = usuarioService.obtenerUsuario(1L);

        assertTrue(result.isPresent());
        assertEquals("test@email.com", result.get().getEmail());
        verify(usuarioRepository).findById(1L); // Verifica que se llamó al repo
    }
}
