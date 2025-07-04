package masterbikes.sucursal_service.service;

import masterbikes.sucursal_service.model.Sucursal;
import masterbikes.sucursal_service.repository.SucursalRepository;
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
 * Test unitario simple para SucursalService usando Mockito.
 * Verifica que el método findAll retorna la lista esperada.
 */
class SucursalServiceTest {
    @Mock
    private SucursalRepository sucursalRepository;
    @InjectMocks
    private SucursalService sucursalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sucursalService = new SucursalService(sucursalRepository);
    }

    @Test
    void testFindAllReturnsList() {
        Sucursal suc = new Sucursal();
        when(sucursalRepository.findAll()).thenReturn(Collections.singletonList(suc));
        List<Sucursal> result = sucursalService.findAll();
        assertEquals(1, result.size());
        verify(sucursalRepository, times(1)).findAll();
    }
}
