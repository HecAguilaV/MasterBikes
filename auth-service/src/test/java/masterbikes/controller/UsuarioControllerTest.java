package masterbikes.controller;

import masterbikes.model.Usuario;
import masterbikes.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    void obtenerUsuario_devuelveUsuarioExistente() throws Exception {
        Usuario usuario = Usuario.builder()
                .id(1L)
                .email("test@email.com")
                .password("1234")
                .nombre("Test User")
                .rol(null)
                .sucursal("CASA_MATRIZ")
                .activo(true)
                .telefono("123456789")
                .direccion("Calle 1")
                .fechaNacimiento("2000-01-01")
                .build();

        Mockito.when(usuarioService.obtenerUsuario(anyLong())).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/usuarios/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("test@email.com"));
    }
}
