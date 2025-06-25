package masterbikes.catalogo_service.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Habilita @PreAuthorize, @PostAuthorize, etc.
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtRequestFilter jwtAuthFilter; // Inyecta el filtro JWT que creamos

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para APIs stateless
            .authorizeHttpRequests(auth -> auth
                // Endpoints públicos (ej. para que cualquiera vea el catálogo)
                // Ajusta estos patrones a tus rutas reales en CatalogoController, BicicletaController, etc.
                .requestMatchers(HttpMethod.GET, "/api/catalogo/**", "/api/bicicletas/**", "/api/accesorios/**", "/api/componentes/**").permitAll()

                // Permitir Swagger/OpenAPI si se añade en el futuro
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/api-docs/**").permitAll()
                .requestMatchers("/error").permitAll() // Permitir acceso a la página de error por defecto

                // Endpoints que requieren rol de ADMIN (ej. para añadir/modificar/eliminar productos)
                // Ajusta estos patrones a tus rutas reales
                .requestMatchers(HttpMethod.POST, "/api/catalogo/**", "/api/bicicletas/**", "/api/accesorios/**", "/api/componentes/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/catalogo/**", "/api/bicicletas/**", "/api/accesorios/**", "/api/componentes/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/catalogo/**", "/api/bicicletas/**", "/api/accesorios/**", "/api/componentes/**").hasRole("ADMIN")

                // Cualquier otra solicitud debe estar autenticada (tener un token JWT válido)
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // No crear ni usar sesiones HTTP
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // Añade el filtro JWT antes del filtro de autenticación estándar
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(authenticationEntryPoint()) // Manejador para errores de autenticación (401)
            );
        return http.build();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        // Devuelve una respuesta 401 Unauthorized personalizada en formato JSON
        return (request, response, authException) -> {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            // Mensaje de error JSON más detallado
            String errorMessage = "{\"timestamp\":\"" + new java.util.Date() + "\","
                                + "\"status\":" + HttpServletResponse.SC_UNAUTHORIZED + ","
                                + "\"error\":\"Unauthorized\","
                                + "\"message\":\"" + authException.getMessage() + // Mensaje de la excepción
                                     (authException.getCause() != null ? " (Cause: " + authException.getCause().getMessage() + ")" : "") + "\","
                                + "\"path\":\"" + request.getRequestURI() + "\"}";
            response.getWriter().write(errorMessage);
        };
    }
}
