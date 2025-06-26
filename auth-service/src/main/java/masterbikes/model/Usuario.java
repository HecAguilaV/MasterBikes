package masterbikes.model;

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
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    @Column(nullable = false)
    private String sucursal; // CASA_MATRIZ, SUCURSAL_1, etc.

    @Builder.Default
    private boolean activo = true;

    @Column(nullable = true)
    private String telefono;

    @Column(nullable = true)
    private String direccion;

    @Column(nullable = true)
    private String fechaNacimiento;
}
