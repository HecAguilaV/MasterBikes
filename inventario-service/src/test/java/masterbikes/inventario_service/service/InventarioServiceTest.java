package masterbikes.inventario_service.service;

import masterbikes.inventario_service.model.Inventario;
import masterbikes.inventario_service.repository.InventarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test unitario simple para InventarioService usando Mockito.
 * Verifica que el método findAll retorna la lista esperada.
 */
class InventarioServiceTest {
    @Mock
    private InventarioRepository inventarioRepository;
    @InjectMocks
    private InventarioService inventarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        inventarioService = new InventarioService(inventarioRepository);
    }

    @Test
    void testFindAllReturnsList() {
        Inventario inv = new Inventario();
        when(inventarioRepository.findAll()).thenReturn(Collections.singletonList(inv));
        List<Inventario> result = inventarioService.findAll();
        assertEquals(1, result.size());
        verify(inventarioRepository, times(1)).findAll();
    }
}
