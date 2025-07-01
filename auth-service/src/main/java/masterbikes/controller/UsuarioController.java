package masterbikes.controller;

import lombok.RequiredArgsConstructor;
import masterbikes.dto.RegistroRequest;
import masterbikes.model.Usuario;
import masterbikes.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
        return usuarioService.obtenerUsuario(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        Usuario creado = usuarioService.crearUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensaje", "Usuario creado correctamente.", "usuario", creado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.actualizarUsuario(id, usuario)
                .map(u -> ResponseEntity.ok(Map.of("mensaje", "Usuario actualizado correctamente.", "usuario", u)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Usuario no encontrado para actualizar.")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        if (usuarioService.eliminarUsuario(id)) {
            return ResponseEntity.ok(Map.of("mensaje", "Usuario desactivado correctamente."));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Usuario no encontrado para desactivar."));
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody RegistroRequest request) {
        if (usuarioService.existeEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", "El email ya está registrado."));
        }
        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .password(request.getPassword())
                .telefono(request.getTelefono())
                .direccion(request.getDireccion())
                .fechaNacimiento(request.getFechaNacimiento())
                .rol(masterbikes.model.enums.Rol.CLIENTE) // Por defecto
                .sucursal("CASA_MATRIZ") // Por defecto, puedes ajustar según lógica
                .build();
        Usuario creado = usuarioService.crearUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensaje", "Usuario registrado correctamente.", "usuario", creado));
    }

    // Cambiar el rol de un usuario (solo admin)
    @PutMapping("/{id}/rol")
    public ResponseEntity<?> cambiarRolUsuario(@PathVariable Long id, @RequestBody Map<String, String> body, @RequestHeader("Authorization") String authHeader) {
        // Validación robusta de rol ADMIN desde el JWT
        String token;
        try {
            token = authHeader.replace("Bearer ", "");
        } catch (Exception e) {
            throw new org.springframework.security.access.AccessDeniedException("Token ausente o malformado");
        }
        Object rolClaim = masterbikes.util.JwtUtilStatic.getClaim(token, "rol");
        if (rolClaim == null || !rolClaim.toString().equals("ADMIN")) {
            throw new org.springframework.security.access.AccessDeniedException("Solo un admin puede cambiar roles");
        }
        String nuevoRol = body.get("rol");
        masterbikes.model.enums.Rol rolEnum;
        try {
            rolEnum = masterbikes.model.enums.Rol.valueOf(nuevoRol);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Rol no válido"));
        }
        return usuarioService.cambiarRol(id, rolEnum)
                .map(usuario -> ResponseEntity.ok(Map.of("mensaje", "Rol actualizado", "usuario", usuario)))
                .orElseThrow(() -> new masterbikes.exception.ResourceNotFoundException("Usuario no encontrado"));
    }
}
