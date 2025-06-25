package masterbikes.venta_service.config;

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
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtRequestFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Ajusta los patrones de ruta y roles según tus controladores y lógica de negocio.
                // Crear una venta
                .requestMatchers(HttpMethod.POST, "/api/ventas/**").hasAnyRole("CLIENTE", "VENDEDOR", "SUPERVISOR", "ADMIN")

                // Ver ventas
                // Para que un CLIENTE vea solo sus ventas, se necesitará lógica adicional en el controlador
                // o una ruta específica como /api/ventas/mis-ventas que se proteja aquí.
                .requestMatchers(HttpMethod.GET, "/api/ventas/cliente/me").hasRole("CLIENTE") // Ejemplo para "mis ventas"
                .requestMatchers(HttpMethod.GET, "/api/ventas/**").hasAnyRole("VENDEDOR", "SUPERVISOR", "ADMIN")

                // Actualizar estado de venta
                .requestMatchers(HttpMethod.PUT, "/api/ventas/**", "/api/ventas/estado/**").hasAnyRole("VENDEDOR", "SUPERVISOR", "ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/ventas/**", "/api/ventas/estado/**").hasAnyRole("VENDEDOR", "SUPERVISOR", "ADMIN")

                // Eliminar/cancelar venta
                .requestMatchers(HttpMethod.DELETE, "/api/ventas/**").hasAnyRole("SUPERVISOR", "ADMIN")

                // Ejemplo para reparaciones (si están en este servicio)
                .requestMatchers(HttpMethod.POST, "/api/reparaciones/**").hasAnyRole("CLIENTE", "VENDEDOR", "SUPERVISOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/reparaciones/tecnico/me").hasRole("TECNICO") // Ejemplo para "mis reparaciones asignadas"
                .requestMatchers(HttpMethod.GET, "/api/reparaciones/**").hasAnyRole("TECNICO", "SUPERVISOR", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/reparaciones/**").hasAnyRole("TECNICO", "SUPERVISOR", "ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/reparaciones/**").hasAnyRole("TECNICO", "SUPERVISOR", "ADMIN")


                // Permitir Swagger/OpenAPI (si se añade) y errores
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/api-docs/**").permitAll()
                .requestMatchers("/error").permitAll()

                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(authenticationEntryPoint())
            );
        return http.build();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            String errorMessage = "{\"timestamp\":\"" + new java.util.Date() + "\","
                                + "\"status\":" + HttpServletResponse.SC_UNAUTHORIZED + ","
                                + "\"error\":\"Unauthorized\","
                                + "\"message\":\"" + authException.getMessage() +
                                     (authException.getCause() != null ? " (Cause: " + authException.getCause().getMessage() + ")" : "") + "\","
                                + "\"path\":\"" + request.getRequestURI() + "\"}";
            response.getWriter().write(errorMessage);
        };
    }
}
