
// Prueba básica de arranque del contexto de Spring Boot para el API Gateway.
// Esta clase verifica que la configuración de la aplicación es válida y que el contexto de Spring se carga correctamente.
// Si hay errores graves de configuración, esta prueba fallará.
// Es útil como "smoke test" automático tras cambios en dependencias o configuración.
package masterbikes.api_gateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest // Indica que se debe iniciar el contexto completo de Spring Boot para la prueba
class ApiGatewayApplicationTests {

	@Test
	void contextLoads() {
		// Si la aplicación no arranca correctamente, esta prueba fallará automáticamente
	}

}
