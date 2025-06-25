package masterbikes.inventario_service.config;

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
                // Ejemplo: Consultas de inventario
                .requestMatchers(HttpMethod.GET, "/api/inventario/**", "/api/stock/**").hasAnyRole("VENDEDOR", "SUPERVISOR", "TECNICO", "ADMIN")

                // Ejemplo: Modificaciones de inventario
                .requestMatchers(HttpMethod.POST, "/api/inventario/**", "/api/stock/**").hasAnyRole("SUPERVISOR", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/inventario/**", "/api/stock/**").hasAnyRole("SUPERVISOR", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/inventario/**", "/api/stock/**").hasAnyRole("SUPERVISOR", "ADMIN")

                // Permitir Swagger/OpenAPI (si se añade) y errores
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/api-docs/**").permitAll()
                .requestMatchers("/error").permitAll()

                // Cualquier otra solicitud requiere autenticación
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
