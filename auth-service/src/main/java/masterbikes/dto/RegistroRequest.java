package masterbikes.dto;

import lombok.Data;

@Data
public class RegistroRequest {
    private String nombre;
    private String email;
    private String password;
    private String telefono;
    private String direccion;
    private String fechaNacimiento;
}
