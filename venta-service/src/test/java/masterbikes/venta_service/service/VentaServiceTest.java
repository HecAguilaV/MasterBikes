package masterbikes.venta_service.service;

import masterbikes.venta_service.model.Venta;
import masterbikes.venta_service.model.DetalleVenta;
import masterbikes.venta_service.repository.VentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test unitario simple para VentaService usando Mockito.
 * Verifica que el método generarVenta llama al repositorio correctamente.
 */
class VentaServiceTest {
    @Mock
    private VentaRepository ventaRepo;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private VentaService ventaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ventaService = new VentaService(ventaRepo, restTemplate);
    }

    @Test
    void testGenerarVentaGuardaVenta() {
        Venta venta = new Venta();
        venta.setDetalles(Collections.emptyList());
        when(ventaRepo.save(any(Venta.class))).thenReturn(venta);
        Venta result = ventaService.generarVenta(venta);
        assertNotNull(result);
        verify(ventaRepo, times(1)).save(venta);
    }
}
