package masterbikes.catalogo_service.controller;

import masterbikes.catalogo_service.model.Bicicleta;
import masterbikes.catalogo_service.service.BicicletaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BicicletaController.class)
class BicicletaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BicicletaService bicicletaService;

    @Test
    void findByIdHateoas_returnsHateoasLinks() throws Exception {
        Bicicleta bici = Bicicleta.builder()
                .id(1L)
                .modelo("Orion")
                .esPredefinida(true)
                .build();

        Mockito.when(bicicletaService.findById(anyLong())).thenReturn(bici);

        mockMvc.perform(get("/api/v1/catalogo/bicicletas/hateoas/1")
                .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.delete.href").exists())
                .andExpect(jsonPath("$._links.all.href").exists());
    }
}
