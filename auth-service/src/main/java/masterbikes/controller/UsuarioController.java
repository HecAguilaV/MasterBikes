package masterbikes.controller;

// Controlador REST para gestión de usuarios (CRUD).
// Expone endpoints para listar, crear, obtener, actualizar y desactivar usuarios.
// La lógica de negocio está delegada en UsuarioService.
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

    /**
     * Lista todos los usuarios registrados.
     * Acceso restringido por rol (ver configuración de seguridad).
     */
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    /**
     * Obtiene un usuario por su ID.
     * @param id identificador del usuario
     * @return usuario si existe, 404 si no
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
        return usuarioService.obtenerUsuario(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo usuario (solo para roles con permisos).
     * @param usuario datos del usuario a crear
     * @return usuario creado y mensaje
     */
    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        Usuario creado = usuarioService.crearUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensaje", "Usuario creado correctamente.", "usuario", creado));
    }

    /**
     * Actualiza un usuario existente.
     * @param id identificador del usuario a actualizar
     * @param usuario datos actualizados del usuario
     * @return usuario actualizado y mensaje, o error 404 si no se encuentra el usuario
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.actualizarUsuario(id, usuario)
                .map(u -> ResponseEntity.ok(Map.of("mensaje", "Usuario actualizado correctamente.", "usuario", u)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Usuario no encontrado para actualizar.")));
    }

    /**
     * Desactiva un usuario (no lo elimina).
     * @param id identificador del usuario a desactivar
     * @return mensaje de éxito o error 404 si no se encuentra el usuario
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        if (usuarioService.eliminarUsuario(id)) {
            return ResponseEntity.ok(Map.of("mensaje", "Usuario desactivado correctamente."));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Usuario no encontrado para desactivar."));
    }

    /**
     * Registra un nuevo usuario desde el frontend (público).
     * @param request datos del registro
     * @return usuario creado y mensaje
     */
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody RegistroRequest request) {
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
}
