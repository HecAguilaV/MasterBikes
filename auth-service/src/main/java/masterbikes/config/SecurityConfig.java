package masterbikes.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // Importar HttpMethod
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.AuthenticationEntryPoint;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity // Habilita la seguridad web de Spring
@EnableMethodSecurity(prePostEnabled = true) // Para usar @PreAuthorize si se necesita
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtRequestFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService; // Inyecta UsuarioService (nuestro UsuarioService implementa UserDetailsService)

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para APIs stateless
            .authorizeHttpRequests(auth -> auth
                // Endpoints públicos: login, swagger, docs, error y registro de usuarios
                .requestMatchers(
                    "/auth/login", // Endpoint de login
                    "/swagger-ui.html", "/swagger-ui/**", // Swagger UI
                    "/v3/api-docs/**", "/api-docs/**", // OpenAPI docs
                    "/error" // Permitir acceso a la página de error por defecto de Spring Boot
                ).permitAll()
                // Permitir el endpoint de registro público para nuevos clientes
                .requestMatchers(HttpMethod.POST, "/api/usuarios/registro").permitAll()
                // Proteger los demás endpoints de /api/usuarios/** para ADMIN
                .requestMatchers("/api/usuarios/**").hasRole("ADMIN")
                // Todas las demás peticiones requieren autenticación
                .anyRequest().authenticated() // Todas las demás peticiones requieren autenticación
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No crear ni usar sesiones HTTP
            )
            .authenticationProvider(authenticationProvider()) // Configura el proveedor de autenticación
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // Añade el filtro JWT
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(authenticationEntryPoint()) // Manejador para errores de autenticación
            );
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // Tu UsuarioService
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Asegúrate de que este Bean también esté disponible para UsuarioService si lo inyecta directamente.
        // En nuestro caso, UsuarioService crea su propia instancia de BCryptPasswordEncoder,
        // lo cual está bien, pero usar un Bean es más estándar en Spring.
        // Por ahora, la configuración actual de UsuarioService es funcional.
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            // Personaliza el mensaje de error JSON
            String errorMessage = "{\"timestamp\":\"" + new java.util.Date() + "\","
                                + "\"status\":" + HttpServletResponse.SC_UNAUTHORIZED + ","
                                + "\"error\":\"Unauthorized\","
                                + "\"message\":\"" + authException.getMessage() + "\","
                                + "\"path\":\"" + request.getRequestURI() + "\"}";
            response.getWriter().write(errorMessage);
        };
    }
}
