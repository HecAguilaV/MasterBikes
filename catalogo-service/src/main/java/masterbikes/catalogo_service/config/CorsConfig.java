package masterbikes.catalogo_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica a todas las rutas del servicio
            .allowedOrigins(
                "http://localhost:8080", // Si tu frontend corre en el puerto 8080
                "http://127.0.0.1:8080", // Alternativa para localhost
                "http://localhost:63342", // Puerto común para IDEs como IntelliJ cuando se abre un HTML
                "null" //  Para `file:///` origins. Usar con precaución.
                // Es mejor usar allowedOriginPatterns para file:// si es posible
                // o servir el frontend desde un servidor web local para desarrollo.
            )
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
            .allowedHeaders("*") // Permite todas las cabeceras
            .allowCredentials(true) // Necesario para enviar cookies o cabeceras de Authorization con el token JWT
            .maxAge(3600); // Tiempo en segundos que el navegador puede cachear la respuesta pre-flight
    }
}
