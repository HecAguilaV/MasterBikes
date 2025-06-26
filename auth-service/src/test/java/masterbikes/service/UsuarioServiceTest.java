package masterbikes.service;

import masterbikes.model.Usuario;
import masterbikes.model.enums.Rol;
import masterbikes.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarUsuarios() {
        Usuario usuario1 = Usuario.builder().id(1L).email("a@a.com").nombre("Ana").password("1234").rol(Rol.CLIENTE).sucursal("CASA_MATRIZ").build();
        Usuario usuario2 = Usuario.builder().id(2L).email("b@b.com").nombre("Benito").password("5678").rol(Rol.VENDEDOR).sucursal("SUCURSAL_1").build();
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario1, usuario2));

        List<Usuario> usuarios = usuarioService.listarUsuarios();
        assertEquals(2, usuarios.size());
        assertEquals("Ana", usuarios.get(0).getNombre());
    }

    @Test
    void testObtenerUsuario() {
        Usuario usuario = Usuario.builder().id(1L).email("a@a.com").nombre("Ana").password("1234").rol(Rol.CLIENTE).sucursal("CASA_MATRIZ").build();
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> result = usuarioService.obtenerUsuario(1L);
        assertTrue(result.isPresent());
        assertEquals("Ana", result.get().getNombre());
    }

    @Test
    void testCrearUsuario() {
        Usuario usuario = Usuario.builder().email("nuevo@a.com").nombre("Nuevo").password("pass").rol(Rol.SUPERVISOR).sucursal("CASA_MATRIZ").build();
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario creado = usuarioService.crearUsuario(usuario);
        assertEquals("Nuevo", creado.getNombre());
    }
}
