package masterbikes.model;

// Entidad JPA que representa a un usuario del sistema.
// Incluye datos personales, credenciales y rol.
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import masterbikes.model.enums.Rol;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email; // Correo electrónico único para login

    @Column(nullable = false)
    private String password; // Contraseña encriptada (BCrypt)

    @Column(nullable = false)
    private String nombre; // Nombre completo

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol; // Rol del usuario (ADMIN, CLIENTE, etc.)

    @Column(nullable = false)
    private String sucursal; // CASA_MATRIZ, SUCURSAL_1, etc.

    @Builder.Default
    private boolean activo = true; // Estado lógico (borrado suave)

    @Column(nullable = true)
    private String telefono; // Número de teléfono de contacto

    @Column(nullable = true)
    private String direccion; // Dirección física

    @Column(nullable = true)
    private String fechaNacimiento; // Fecha de nacimiento

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Es importante prefijar con "ROLE_" si se usan expresiones de rol como hasRole('ADMIN')
        // o si se usa @PreAuthorize("hasRole('ADMIN')")
        // Si solo se usa .requestMatchers(HttpMethod.GET, "/ruta").hasAuthority("ADMIN"), no es necesario.
        // Por consistencia y flexibilidad, es buena práctica añadir "ROLE_".
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + rol.name()));
    }

    @Override
    public String getUsername() {
        return email; // Usamos email como username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Por defecto, no expira
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Por defecto, no bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Por defecto, credenciales no expiran
    }

    @Override
    public boolean isEnabled() {
        return activo; // Usa el campo 'activo' de la entidad
    }
}
