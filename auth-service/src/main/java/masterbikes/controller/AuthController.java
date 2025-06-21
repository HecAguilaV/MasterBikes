package masterbikes.controller;

// Controlador REST para autenticación de usuarios.
// Expone el endpoint de login y genera el JWT para el usuario autenticado.
// Utiliza BCrypt para validar contraseñas y JwtUtil para emitir el token.
import lombok.RequiredArgsConstructor;
import masterbikes.dto.LoginRequest;
import masterbikes.dto.LoginResponse;
import masterbikes.model.Usuario;
import masterbikes.repository.UsuarioRepository;
import masterbikes.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Endpoint para login de usuario.
     * @param request LoginRequest con email y password.
     * @return JWT y rol si es exitoso, error si las credenciales no coinciden.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(request.getEmail()))
                .findFirst().orElse(null);
        if (usuario == null || !passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            // Seguridad: nunca revelar si el email existe o no.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales inválidas. Verifica tu email y contraseña."));
        }
        // Genera el JWT con claims personalizados (rol y sucursal)
        String token = jwtUtil.generateToken(usuario.getEmail(), Map.of(
                "rol", usuario.getRol().name(),
                "sucursal", usuario.getSucursal()
        ));
        return ResponseEntity.ok(new LoginResponse(token, usuario.getRol().name(), "Login exitoso."));
    }
}
