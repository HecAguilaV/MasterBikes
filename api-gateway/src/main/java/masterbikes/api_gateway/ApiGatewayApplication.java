
// Clase principal de arranque del microservicio API Gateway.
// Esta clase inicializa el contexto de Spring Boot y pone en marcha el Gateway,
// que se encargará de enrutar las peticiones a los microservicios correspondientes
// según la configuración definida (application.yml).
// Aunque parece "vacía", es fundamental: sin ella, el microservicio no se ejecuta.
//
// El decorador @SpringBootApplication habilita:
//  - Escaneo automático de componentes
//  - Configuración automática
//  - Soporte para Spring Cloud Gateway

package masterbikes.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Marca la clase como punto de entrada de una aplicación Spring Boot
public class ApiGatewayApplication {

	public static void main(String[] args) {
		// Método principal: inicia la aplicación Spring Boot
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
