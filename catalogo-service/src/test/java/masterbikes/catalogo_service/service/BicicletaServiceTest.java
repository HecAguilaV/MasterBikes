package masterbikes.catalogo_service.service;

import masterbikes.catalogo_service.model.Bicicleta;
import masterbikes.catalogo_service.model.Componente;
import masterbikes.catalogo_service.repository.BicicletaRepository;
import masterbikes.catalogo_service.repository.ComponenteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BicicletaServiceTest {
    @Mock
    private BicicletaRepository bicicletaRepository;
    @Mock
    private ComponenteRepository componenteRepository;
    @InjectMocks
    private BicicletaService bicicletaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bicicletaService = new BicicletaService(bicicletaRepository, componenteRepository);
    }

    @Test
    void testFindAllReturnsList() {
        Bicicleta bici = new Bicicleta();
        when(bicicletaRepository.findAll()).thenReturn(Collections.singletonList(bici));
        List<Bicicleta> result = bicicletaService.findAll();
        assertEquals(1, result.size());
        verify(bicicletaRepository, times(1)).findAll();
    }

    @Test
    void testSaveThrowsExceptionIfComponentesNull() {
        Bicicleta bici = new Bicicleta();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> bicicletaService.save(bici));
        assertTrue(exception.getMessage().contains("obligatorios"));
    }
}
