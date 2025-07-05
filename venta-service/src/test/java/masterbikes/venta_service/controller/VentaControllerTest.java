package masterbikes.venta_service.controller;

import masterbikes.venta_service.model.Venta;
import masterbikes.venta_service.service.VentaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VentaController.class)
class VentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VentaService ventaService;

    @Test
    void getVentaHateoas_returnsHateoasLinks() throws Exception {
        Venta venta = Venta.builder()
                .id(1L)
                .fecha(LocalDateTime.now())
                .clienteId(100L)
                .sucursalId(10L)
                .vendedorId(5L)
                .total(50000.0)
                .medioPago("EFECTIVO")
                .detalles(Collections.emptyList())
                .factura(null)
                .build();

        Mockito.when(ventaService.findById(anyLong())).thenReturn(venta);

        mockMvc.perform(get("/api/v1/ventas/1/hateoas")
                .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.ventas.href").exists());
    }
}
