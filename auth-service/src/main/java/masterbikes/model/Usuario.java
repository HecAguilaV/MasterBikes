package masterbikes.model;

// Entidad JPA que representa a un usuario del sistema.
// Incluye datos personales, credenciales y rol.
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import masterbikes.model.enums.Rol;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
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
}
